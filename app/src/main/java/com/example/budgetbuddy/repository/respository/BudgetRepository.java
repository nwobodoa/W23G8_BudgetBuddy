package com.example.budgetbuddy.repository.respository;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Transaction;

import com.example.budgetbuddy.model.Budget;
import com.example.budgetbuddy.model.BudgetWithLineItems;
import com.example.budgetbuddy.model.LineItem;
import com.example.budgetbuddy.repository.AppDatabase;
import com.example.budgetbuddy.repository.dao.BudgetDao;
import com.example.budgetbuddy.repository.dao.LineItemDao;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class BudgetRepository {

    private final BudgetDao budgetDao;
    private final LineItemDao lineItemDao;

    private final MutableLiveData<List<Long>> savedIds = new MutableLiveData<>();

    public BudgetRepository(Application application) {
        this.budgetDao = AppDatabase.getDatabase(application).budgetDao();
        this.lineItemDao = AppDatabase.getDatabase(application).lineItemDao();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Transaction
    public void insert(List<LineItem> lineItems) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Optional<LineItem> optionalLineItem = Optional.of(lineItems.get(0));
                Budget budget;
                if (optionalLineItem.get().getBudgetId() != null) {
                    budget = new Budget(optionalLineItem.get().getBudgetId(), LocalDate.now(), YearMonth.now());
                } else {
                    budget = new Budget(LocalDate.now(), YearMonth.now());
                }

                long budgetId = budgetDao.insert(budget);
                lineItems.forEach(lineItem -> {
                    lineItem.setBudgetId(budgetId);
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

    public LiveData<Budget> getBudget(YearMonth yearMonth) {
        return budgetDao.getBudget(yearMonth);
    }

    public LiveData<List<LineItem>> getLineItems(Budget budget) {
        return lineItemDao.getLineItems(budget.getId());
    }

    public LiveData<BudgetWithLineItems> getBudgetWithLineItems(YearMonth yearMonth) {
        return budgetDao.getBudgetWithLineItems(yearMonth);
    }
}
