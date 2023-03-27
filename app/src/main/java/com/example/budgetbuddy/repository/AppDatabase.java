package com.example.budgetbuddy.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.budgetbuddy.converter.LocalDateConverter;
import com.example.budgetbuddy.model.Budget;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.model.User;
import com.example.budgetbuddy.repository.dao.BudgetDao;
import com.example.budgetbuddy.repository.dao.TransactionDao;
import com.example.budgetbuddy.repository.dao.UserDao;

@Database(entities = {User.class, Transaction.class, Budget.class}, version = 5)
@TypeConverters({LocalDateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao   userDao();

    public abstract BudgetDao budgetDao();

    public abstract TransactionDao transactionDao();
}