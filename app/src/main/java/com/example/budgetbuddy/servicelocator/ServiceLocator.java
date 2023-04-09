package com.example.budgetbuddy.servicelocator;

import android.content.Context;

import androidx.room.Room;

import com.example.budgetbuddy.repository.AppDatabase;
import com.example.budgetbuddy.repository.dao.BudgetDao;
import com.example.budgetbuddy.repository.dao.TransactionDao;
import com.example.budgetbuddy.repository.dao.UserDao;

public class ServiceLocator {
    private static ServiceLocator instance = null;
    private static final AppDatabase db = null;
    private ServiceLocator(){
    }

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public AppDatabase getDb(Context ctx) {
        return AppDatabase.getDatabase(ctx);
    }

    public UserDao getUserDao(Context ctx) {
        return getDb(ctx).userDao();
    }

    public TransactionDao getTransactionDao(Context ctx) {
        return  getDb(ctx).transactionDao();
    }

    public BudgetDao getBudgetDao(Context ctx) {
        return  getDb(ctx).budgetDao();
    }
}
