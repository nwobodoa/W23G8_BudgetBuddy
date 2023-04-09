package com.example.budgetbuddy.ui.budget;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.budgetbuddy.model.Budget;
import com.example.budgetbuddy.model.Category;
import com.example.budgetbuddy.model.LineItem;
import com.example.budgetbuddy.repository.respository.BudgetRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class BudgetViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mText;

    private final BudgetRepository budgetRepository;

    public BudgetViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is Add Budget fragment");
        budgetRepository = new BudgetRepository(application);
    }

    public LiveData<String> getText() {
        return mText;
    }


    public void saveLineItems(List<LineItem> filledLineItems) {
        budgetRepository.insert(filledLineItems);
    }

    public LiveData<List<Long>> getSavedLineItemIds() {
        return budgetRepository.getSavedTransactionIds();
    }

    public LiveData<Budget> getBudget() {
        return budgetRepository.getBudget(YearMonth.now());
    }

    private List<LineItem> addData() {
        return Category.getAllCategories().stream().map(category -> new LineItem(0,category)).collect(Collectors.toList());

    }
    public LiveData<List<LineItem>> getLineItems(Budget budget) {
        if(budget == null) {
            MutableLiveData<List<LineItem>>  items = new MutableLiveData<>();
            items.postValue(addData());
            return  items;
        }
     return    budgetRepository.getLineItems(budget);
    }
}


