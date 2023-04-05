package com.example.budgetbuddy.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.budgetbuddy.model.Category;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.model.TransactionByCategory;

import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM `transaction` order by createdAt DESC LIMIT 50")
    LiveData<List<Transaction>> getAll();
    @Insert
    List<Long> insertAll(Transaction... transactions);
    @Query("select category, sum(amount) as total from `transaction` " +
            "where  substr(createdAt,4,2)  = strftime('%m', date('now')) " +
            "and  substr(createdAt,7) = strftime('%Y', date('now'))  group by category;")
    LiveData<List<TransactionByCategory>> getTransactionsSummaryByCategory();

    @Query("SELECT * FROM `transaction` where category = :category and " +
            "substr(createdAt,4,2)  = strftime('%m', date('now')) and substr(createdAt,7) = strftime('%Y', date('now'))" +
            "order by createdAt DESC LIMIT 50")
    LiveData<List<Transaction>> getTransactions(Category category);
}

