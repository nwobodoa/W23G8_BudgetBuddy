package com.example.budgetbuddy.ui.add_expense;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.repository.respository.TransactionRepository;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
   private final TransactionRepository mRepository;
    public ExpenseViewModel(Application application) {
        super(application);
        mRepository =  new TransactionRepository(application);

    }
    public LiveData<List<Transaction>> getTransactions() {
        return mRepository.getAllTransactions();
    }

    public void insert(MutableLiveData<List<Long>> savedTransactionIds, Transaction... transactions) {
        mRepository.insert(savedTransactionIds,transactions);
    }
}
