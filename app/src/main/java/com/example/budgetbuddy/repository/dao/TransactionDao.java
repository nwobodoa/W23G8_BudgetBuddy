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
    @Query("SELECT * FROM `transaction` ORDER BY createdAt DESC")
    LiveData<List<Transaction>> getAll();
    @Insert
    List<Long> insertAll(Transaction... transactions);
    @Query("SELECT category, sum(amount) AS total FROM `transaction` " +
            "WHERE  substr(createdAt,4,2)  = strftime('%m', date('now')) " +
            "AND  substr(createdAt,7) = strftime('%Y', date('now'))  GROUP BY category;")
    LiveData<List<TransactionByCategory>> getTransactionsSummaryByCategory();


    @Query("SELECT * FROM `transaction` WHERE category = :category AND " +
            "substr(createdAt,4,2)  = strftime('%m', date('now')) AND substr(createdAt,7) = strftime('%Y', date('now'))" +
            "ORDER BY createdAt DESC")
    LiveData<List<Transaction>> getTransactions(Category category);
}

