package com.example.budgetbuddy.ui.spendingbycat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SpendingByCatViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public SpendingByCatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}


