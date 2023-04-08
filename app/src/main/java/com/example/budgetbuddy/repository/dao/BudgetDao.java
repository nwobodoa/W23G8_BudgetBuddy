package com.example.budgetbuddy.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.budgetbuddy.model.Budget;
import com.example.budgetbuddy.model.BudgetWithLineItems;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Dao
public interface BudgetDao {
    @Query("SELECT * FROM BUDGET where createdAt between :start and :end")
    List<Budget> findBudgetBetweenDates(LocalDate start, LocalDate end);
    @Query("SELECT * FROM BUDGET LIMIT 50")
    List<Budget> getAll();

    @Transaction
    @Query("SELECT * FROM Budget WHERE yearMonth = :yearMonth")
    LiveData<BudgetWithLineItems> getBudgetWithLineItems(YearMonth yearMonth);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Budget... budgets);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Budget budget);

    @Query("SELECT * FROM budget WHERE yearMonth = :yearMonth")
    LiveData<Budget> getBudget(YearMonth yearMonth);
}
