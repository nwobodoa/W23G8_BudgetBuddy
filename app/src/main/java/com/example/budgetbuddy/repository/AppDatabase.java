package com.example.budgetbuddy.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.budgetbuddy.converter.LocalDateConverter;
import com.example.budgetbuddy.model.Budget;
import com.example.budgetbuddy.model.Expense;
import com.example.budgetbuddy.model.Income;
import com.example.budgetbuddy.model.User;
import com.example.budgetbuddy.repository.dao.BudgetDao;
import com.example.budgetbuddy.repository.dao.ExpenseDao;
import com.example.budgetbuddy.repository.dao.IncomeDao;
import com.example.budgetbuddy.repository.dao.UserDao;

@Database(entities = { Budget.class, Expense.class, Income.class, User.class}, version = 6)
@TypeConverters({LocalDateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract BudgetDao budgetDao();
    public abstract ExpenseDao expenseDao();
    public abstract IncomeDao incomeDao();
    public abstract UserDao   userDao();
}