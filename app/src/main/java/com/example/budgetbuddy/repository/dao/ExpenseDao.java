package com.example.budgetbuddy.repository.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.budgetbuddy.model.Expense;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface ExpenseDao {
    @Query("SELECT * FROM Expense where expense_date between :start and :end")
    List<Expense> findExpenseBetweenDates(LocalDate start, LocalDate end);
    @Query("SELECT * FROM EXPENSE LIMIT 50")
    List<Expense> getAll();
    @Insert
    void insertAll(Expense... expenses);
}
