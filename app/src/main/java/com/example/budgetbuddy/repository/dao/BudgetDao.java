package com.example.budgetbuddy.repository.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.budgetbuddy.model.Budget;
import com.example.budgetbuddy.model.Income;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface BudgetDao {
//    @Query("SELECT * FROM BUDGET where createdAt between :start and :end")
//    List<Budget> findBudgetBetweenDates(LocalDate start, LocalDate end);
    @Query("SELECT * FROM BUDGET LIMIT 50")
    List<Budget> getAll();
    @Insert
    void insertAll(Budget... budgets);
}
