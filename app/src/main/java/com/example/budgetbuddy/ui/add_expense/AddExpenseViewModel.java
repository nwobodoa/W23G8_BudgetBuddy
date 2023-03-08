package com.example.budgetbuddy.ui.add_expense;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddExpenseViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddExpenseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}