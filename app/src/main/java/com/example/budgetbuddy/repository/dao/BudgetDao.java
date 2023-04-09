package com.example.budgetbuddy.repository.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.budgetbuddy.model.Budget;

import java.util.List;

@Dao
public interface BudgetDao {
//    @Query("SELECT * FROM BUDGET where createdAt between :start and :end")
//    List<Budget> findBudgetBetweenDates(LocalDate start, LocalDate end);
    @Query("SELECT * FROM BUDGET LIMIT 50")
    List<Budget> getAll();

    @Query("SELECT COUNT(*) FROM budget")
    int getBudgetCount();

    @Insert
    void insertAll(Budget... budgets);

    @Update
    int updateAll(List<Budget> budgets);

    @Query("DELETE FROM budget")
    int deleteAll();


}
