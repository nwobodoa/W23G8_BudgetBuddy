package com.example.budgetbuddy.repository.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.model.TransactionByCategory;
import com.example.budgetbuddy.repository.AppDatabase;
import com.example.budgetbuddy.repository.dao.TransactionDao;

import java.util.List;

public class TransactionRepository {
    private final TransactionDao transactionDao;
    private final LiveData<List<Transaction>> mTransactions;

   public TransactionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        transactionDao = db.transactionDao();
        mTransactions = transactionDao.getAll();
    }

   public LiveData<List<Transaction>> getTransactions() {
        return mTransactions;
    }

    public LiveData<List<TransactionByCategory>> getSpendingByCategory() {
       return transactionDao.getTransactionsSummaryByCategory();
    }
   public void insert(MutableLiveData<List<Long>> transactionIds, Transaction... transactions) {
      AppDatabase.databaseWriteExecutor.execute(() -> transactionIds.postValue(transactionDao.insertAll(transactions)));
    }
}
