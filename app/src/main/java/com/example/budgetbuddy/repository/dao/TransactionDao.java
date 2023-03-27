package com.example.budgetbuddy.repository.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.budgetbuddy.model.Income;
import com.example.budgetbuddy.model.Transaction;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM `transaction` order by createdAt DESC LIMIT 50")
    List<Transaction> getAll();
    @Insert
    void insertAll(Transaction... transactions);
}
