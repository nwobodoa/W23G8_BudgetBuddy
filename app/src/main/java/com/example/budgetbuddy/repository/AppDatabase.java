package com.example.budgetbuddy.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.budgetbuddy.converter.LocalDateConverter;
import com.example.budgetbuddy.converter.MonthYearConverter;
import com.example.budgetbuddy.model.Allocation;
import com.example.budgetbuddy.model.Budget;
import com.example.budgetbuddy.model.LineItem;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.model.User;
import com.example.budgetbuddy.repository.dao.AllocationDao;
import com.example.budgetbuddy.repository.dao.BudgetDao;
import com.example.budgetbuddy.repository.dao.LineItemDao;
import com.example.budgetbuddy.repository.dao.TransactionDao;
import com.example.budgetbuddy.repository.dao.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Transaction.class, Allocation.class, LineItem.class, Budget.class}, version = 8)
@TypeConverters({LocalDateConverter.class, MonthYearConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao   userDao();

    public abstract AllocationDao allocationDao();

    public abstract TransactionDao transactionDao();

    public abstract LineItemDao lineItemDao();

    public abstract BudgetDao budgetDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //Singleton pattern to ensure that the app on has one instance at a time
    // synchronized is to ensure that different threads are aware of the singleton for thread safety
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "budget_buddy")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries() //used to accommodate smart's version
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
