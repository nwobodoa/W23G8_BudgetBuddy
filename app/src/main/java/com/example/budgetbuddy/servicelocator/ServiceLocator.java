package com.example.budgetbuddy.servicelocator;

import android.content.Context;

import androidx.room.Room;

import com.example.budgetbuddy.repository.AppDatabase;
import com.example.budgetbuddy.repository.dao.IncomeDao;

public class ServiceLocator {
    private static ServiceLocator instance = null;
    private static AppDatabase db = null;
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
        if(db == null) {
            synchronized (ServiceLocator.class) {
                db = Room.databaseBuilder(ctx, AppDatabase.class, "budget_buddy").build();
            }
        }
        return db;
    }

    public IncomeDao getIncomeDao(Context ctx) {
        return getDb(ctx).incomeDao();
    }
}
