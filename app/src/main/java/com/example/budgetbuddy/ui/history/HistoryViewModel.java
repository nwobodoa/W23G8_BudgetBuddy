package com.example.budgetbuddy.ui.history;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.repository.respository.TransactionRepository;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {

    private final TransactionRepository transactionRepository;

    public HistoryViewModel(Application application) {
        super(application);

        transactionRepository = new TransactionRepository(application);
    }



    public LiveData<List<Transaction>> getAllTransactions() {
        return transactionRepository.getAllTransactions();
    }
}
