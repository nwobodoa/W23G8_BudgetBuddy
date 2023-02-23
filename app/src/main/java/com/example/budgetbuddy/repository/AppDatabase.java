package com.example.budgetbuddy.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.budgetbuddy.converter.LocalDateConverter;
import com.example.budgetbuddy.model.Income;
import com.example.budgetbuddy.repository.dao.IncomeDao;

@Database(entities = {Income.class}, version = 1)
@TypeConverters({LocalDateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract IncomeDao incomeDao();
}