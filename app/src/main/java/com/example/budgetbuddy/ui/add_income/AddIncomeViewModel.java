package com.example.budgetbuddy.ui.add_income;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddIncomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddIncomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Add Income");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
