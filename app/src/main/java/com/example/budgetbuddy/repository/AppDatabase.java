package com.example.budgetbuddy.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.budgetbuddy.converter.LocalDateConverter;
import com.example.budgetbuddy.model.Expense;
import com.example.budgetbuddy.model.Income;
import com.example.budgetbuddy.model.User;
import com.example.budgetbuddy.repository.dao.ExpenseDao;
import com.example.budgetbuddy.repository.dao.IncomeDao;
import com.example.budgetbuddy.repository.dao.UserDao;
import com.example.budgetbuddy.ui.add_income.IncomeFragment;

@Database(entities = {Income.class, Expense.class, User.class}, version = 3)
@TypeConverters({LocalDateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExpenseDao expenseDao();
    public abstract IncomeDao incomeDao();
    public abstract UserDao   userDao();
}