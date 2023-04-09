package com.example.budgetbuddy.ui.allocation;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.budgetbuddy.model.Allocation;
import com.example.budgetbuddy.model.AllocationWithLineItems;
import com.example.budgetbuddy.model.Category;
import com.example.budgetbuddy.model.LineItem;
import com.example.budgetbuddy.model.SummaryItem;
import com.example.budgetbuddy.model.TransactionByCategory;
import com.example.budgetbuddy.repository.respository.AllocationRepository;
import com.example.budgetbuddy.repository.respository.TransactionRepository;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class AllocationViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mText;
    private final TransactionRepository transactionRepository;
    private final AllocationRepository allocationRepository;
    public AllocationViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
        allocationRepository = new AllocationRepository(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is Add Budget fragment");
    }

    public LiveData<List<SummaryItem>> getBudgetSummary() {
       return Transformations.switchMap(allocationRepository.getBudgetWithLineItems(YearMonth.now()), this::calculateSummary);
    }

    private List<SummaryItem> calculateSummary(List<TransactionByCategory> trx, Map<Category, LineItem> categoryLineItemMap ) {
      var normalizedTrx =   trx.stream().peek(tx -> tx.total = Math.abs(tx.total));
        Map<Category,Double> transactionByCategoryMap = new HashMap<>();
        normalizedTrx.forEach(transactionByCategory -> transactionByCategoryMap.put(transactionByCategory.category,transactionByCategory.total));

        List<SummaryItem> summaryItems = new ArrayList<>();

        categoryLineItemMap.forEach((category,lineItem) -> {
            var actualSpend = transactionByCategoryMap.get(category) == null? Double.valueOf(0.0) : transactionByCategoryMap.get(category);
            summaryItems.add(new SummaryItem(category,lineItem.getAmount(),actualSpend));
        });
        return summaryItems;
    }

    private LiveData<List<SummaryItem>> calculateSummary(@Nullable AllocationWithLineItems budget) {
        Map<Category, LineItem> categoryLineItemMap =  new HashMap<>();
        if(budget != null) {
            budget.getLineItems().forEach(lineItem -> categoryLineItemMap.put(lineItem.getCategory(),lineItem));
        }
     return  Transformations.map(transactionRepository.getSpendingByCategory(), trx -> calculateSummary(trx,categoryLineItemMap));
    }

    public LiveData<String> getText() {
        return mText;
    }


    public void saveLineItems(List<LineItem> filledLineItems) {
        allocationRepository.insert(filledLineItems);
    }

    public LiveData<List<Long>> getSavedLineItemIds() {
        return allocationRepository.getSavedTransactionIds();
    }

    public LiveData<Allocation> getBudget() {
        return allocationRepository.getBudget(YearMonth.now());
    }

    private List<LineItem> addData() {
        return Category.getAllCategories().stream().map(category -> new LineItem(0,category)).collect(Collectors.toList());

    }
    public LiveData<List<LineItem>> getLineItems(Allocation allocation) {
        if(allocation == null) {
            MutableLiveData<List<LineItem>> items = new MutableLiveData<>();
            items.postValue(addData());
            return  items;
        }
        return allocationRepository.getLineItems(allocation);
    }
}
