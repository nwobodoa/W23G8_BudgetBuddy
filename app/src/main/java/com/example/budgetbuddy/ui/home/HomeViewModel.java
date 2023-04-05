package com.example.budgetbuddy.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.model.TransactionByCategory;
import com.example.budgetbuddy.repository.dao.TransactionDao;
import com.example.budgetbuddy.repository.respository.TransactionRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private final TransactionRepository transactionRepository;
    public HomeViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
    }

    public LiveData<List<TransactionByCategory>> getSpendingByCategory() {
        return transactionRepository.getSpendingByCategory();
    }
}
