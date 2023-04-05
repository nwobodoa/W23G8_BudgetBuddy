package com.example.budgetbuddy.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.budgetbuddy.model.Income;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.model.TransactionByCategory;

import java.time.LocalDate;
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

}

// |   |  |