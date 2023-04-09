package com.example.budgetbuddy.repository.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.budgetbuddy.model.Category;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.model.TransactionByCategory;
import com.example.budgetbuddy.repository.AppDatabase;
import com.example.budgetbuddy.repository.dao.TransactionDao;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionRepository {
    private final TransactionDao transactionDao;

   public TransactionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        transactionDao = db.transactionDao();
    }

   public LiveData<List<Transaction>> getAllTransactions() {
        return transactionDao.getAll();
    }

    public LiveData<List<TransactionByCategory>> getSpendingByCategory() {
       return Transformations.map(transactionDao.getTransactionsSummaryByCategory(), this::filterExpensesOnly);
    }

    private List<TransactionByCategory> filterExpensesOnly(List<TransactionByCategory> transactionByCategories) {
       return transactionByCategories.stream()
                .filter(transactionByCategory -> transactionByCategory.category != Category.INCOME)
                .collect(Collectors.toList());
    }

    private List<TransactionByCategory> filterIncome(List<TransactionByCategory> transactionByCategories) {
        return transactionByCategories.stream()
                .filter(transactionByCategory -> transactionByCategory.category == Category.INCOME)
                .collect(Collectors.toList());
    }

   public void insert(MutableLiveData<List<Long>> transactionIds, Transaction... transactions) {
      AppDatabase.databaseWriteExecutor.execute(() -> transactionIds.postValue(transactionDao.insertAll(transactions)));
    }

    public LiveData<List<Transaction>> getAllTransactions(Category category) {
       return transactionDao.getTransactions(category);
    }
}
