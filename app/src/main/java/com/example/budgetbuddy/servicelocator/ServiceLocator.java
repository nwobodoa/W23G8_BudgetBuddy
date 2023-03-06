package com.example.budgetbuddy.servicelocator;

import android.content.Context;

import androidx.room.Room;

import com.example.budgetbuddy.repository.AppDatabase;
import com.example.budgetbuddy.repository.dao.IncomeDao;
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
     return Room.databaseBuilder(ctx, AppDatabase.class, "budget_buddy")
                .fallbackToDestructiveMigration()
                //TODO: Handle login out of main thread
                .allowMainThreadQueries()
                .build();
    }

    public IncomeDao getIncomeDao(Context ctx) {
        return getDb(ctx).incomeDao();
    }

    public UserDao getUserDao(Context ctx) {
        return getDb(ctx).userDao();
    }
}
