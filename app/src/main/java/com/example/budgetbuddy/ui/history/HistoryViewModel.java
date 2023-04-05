package com.example.budgetbuddy.ui.history;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.repository.respository.TransactionRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private final MutableLiveData<String> mText;
    private final TransactionRepository transactionRepository;

    public HistoryViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is Add Spending History fragment");
        transactionRepository = new TransactionRepository(application);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }
}
