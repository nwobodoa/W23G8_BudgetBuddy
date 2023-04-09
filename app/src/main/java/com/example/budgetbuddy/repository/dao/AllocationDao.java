package com.example.budgetbuddy.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.budgetbuddy.model.Allocation;
import com.example.budgetbuddy.model.AllocationWithLineItems;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Dao
public interface AllocationDao {
    @Query("SELECT * FROM Allocation where createdAt between :start and :end")
    List<Allocation> findBudgetBetweenDates(LocalDate start, LocalDate end);
    @Query("SELECT * FROM Allocation LIMIT 50")
    List<Allocation> getAll();

    @Transaction
    @Query("SELECT * FROM Allocation WHERE yearMonth = :yearMonth")
    LiveData<AllocationWithLineItems> getBudgetWithLineItems(YearMonth yearMonth);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Allocation... allocations);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Allocation allocation);

    @Query("SELECT * FROM Allocation WHERE yearMonth = :yearMonth")
    LiveData<Allocation> getBudget(YearMonth yearMonth);
}
