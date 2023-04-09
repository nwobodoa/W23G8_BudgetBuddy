package com.example.budgetbuddy.ui.spendingbycat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.budgetbuddy.model.Category;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.model.TransactionByCategory;
import com.example.budgetbuddy.repository.respository.TransactionRepository;

import java.util.List;

public class SpendingByCatViewModel extends AndroidViewModel {


    private final TransactionRepository transactionRepository;
    private final MutableLiveData<List<Transaction>> transactionsForCategory = new MutableLiveData<>();
    public SpendingByCatViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);

    }

        public LiveData<List<TransactionByCategory>> getSpendingByCategory() {
        return transactionRepository.getSpendingByCategory();
    }

    public LiveData<List<Transaction>> getTransactions(Category category) {
        return transactionRepository.getAllTransactions(category);
    }

    public MutableLiveData<List<Transaction>> getTransactionsForCategory() {
        return transactionsForCategory;
    }

    public void  updateTransactionsForCategory(List<Transaction> transactions) {
        transactionsForCategory.postValue(transactions);
    }

    public LiveData<List<Transaction>> getTransactionsForCategory(Category category) {
        return transactionRepository.getAllTransactions(category);
    }
}


