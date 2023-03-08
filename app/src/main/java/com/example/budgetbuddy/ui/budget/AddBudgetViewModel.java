package com.example.budgetbuddy.ui.budget;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddBudgetViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AddBudgetViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Add Budget fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}


