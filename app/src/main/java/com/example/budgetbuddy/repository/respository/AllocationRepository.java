package com.example.budgetbuddy.repository.respository;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Transaction;

import com.example.budgetbuddy.model.Allocation;
import com.example.budgetbuddy.model.AllocationWithLineItems;
import com.example.budgetbuddy.model.LineItem;
import com.example.budgetbuddy.repository.AppDatabase;
import com.example.budgetbuddy.repository.dao.AllocationDao;
import com.example.budgetbuddy.repository.dao.LineItemDao;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class AllocationRepository {

    private final AllocationDao budgetDao;
    private final LineItemDao lineItemDao;

    private final MutableLiveData<List<Long>> savedIds = new MutableLiveData<>();

    public AllocationRepository(Application application) {
        this.budgetDao = AppDatabase.getDatabase(application).budgetDao();
        this.lineItemDao = AppDatabase.getDatabase(application).lineItemDao();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Transaction
    public void insert(List<LineItem> lineItems) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Optional<LineItem> optionalLineItem = Optional.of(lineItems.get(0));
                Allocation allocation;
                if (optionalLineItem.get().getAllocationId() != null) {
                    allocation = new Allocation(optionalLineItem.get().getAllocationId(), LocalDate.now(), YearMonth.now());
                } else {
                    allocation = new Allocation(LocalDate.now(), YearMonth.now());
                }

                long budgetId = budgetDao.insert(allocation);
                lineItems.forEach(lineItem -> {
                    lineItem.setAllocationId(budgetId);
                });
                List<Long> lineItemIds = lineItemDao.insertAll(lineItems);
                savedIds.postValue(lineItemIds);
            } catch (Exception e) {
                savedIds.postValue(List.of());
                Log.e(TAG, "insert budget: ", e);
            }
        });

    }

    public LiveData<List<Long>> getSavedTransactionIds() {
        return savedIds;
    }

    public LiveData<Allocation> getBudget(YearMonth yearMonth) {
        return budgetDao.getBudget(yearMonth);
    }

    public LiveData<List<LineItem>> getLineItems(Allocation allocation) {
        return lineItemDao.getLineItems(allocation.getId());
    }

    public LiveData<AllocationWithLineItems> getBudgetWithLineItems(YearMonth yearMonth) {
        return budgetDao.getBudgetWithLineItems(yearMonth);
    }
}
