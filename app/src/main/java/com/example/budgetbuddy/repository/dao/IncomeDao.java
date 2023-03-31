package com.example.budgetbuddy.repository.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.budgetbuddy.model.Income;

import java.time.LocalDate;
import java.util.List;

@Dao
public interface IncomeDao {
    @Query("SELECT * FROM INCOME where pay_date between :start and :end")
    List<Income> findIncomeBetweenDates(LocalDate start, LocalDate end);
    @Query("SELECT * FROM INCOME LIMIT 50")
    List<Income> getAll();
    @Insert
    void insertAll(Income... incomes);
}
