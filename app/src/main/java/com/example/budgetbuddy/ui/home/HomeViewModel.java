package com.example.budgetbuddy.ui.home;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.budgetbuddy.model.BudgetWithLineItems;
import com.example.budgetbuddy.model.Category;
import com.example.budgetbuddy.model.LineItem;
import com.example.budgetbuddy.model.SummaryItem;
import com.example.budgetbuddy.model.TransactionByCategory;
import com.example.budgetbuddy.repository.respository.BudgetRepository;
import com.example.budgetbuddy.repository.respository.TransactionRepository;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends AndroidViewModel {
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;
    public HomeViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
        budgetRepository = new BudgetRepository(application);
    }

    public LiveData<List<SummaryItem>> getBudgetSummary() {
       return Transformations.switchMap(budgetRepository.getBudgetWithLineItems(YearMonth.now()), this::calculateSummary);
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

    private LiveData<List<SummaryItem>> calculateSummary(@Nullable BudgetWithLineItems budget) {
        Map<Category, LineItem> categoryLineItemMap =  new HashMap<>();
        if(budget != null) {
            budget.getLineItems().forEach(lineItem -> categoryLineItemMap.put(lineItem.getCategory(),lineItem));
        }
     return  Transformations.map(transactionRepository.getSpendingByCategory(), trx -> calculateSummary(trx,categoryLineItemMap));
    }
}
