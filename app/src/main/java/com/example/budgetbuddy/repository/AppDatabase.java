package com.example.budgetbuddy.repository;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.budgetbuddy.converter.LocalDateConverter;
import com.example.budgetbuddy.converter.MonthYearConverter;
import com.example.budgetbuddy.model.Budget;
import com.example.budgetbuddy.model.LineItem;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.model.User;
import com.example.budgetbuddy.repository.dao.BudgetDao;
import com.example.budgetbuddy.repository.dao.TransactionDao;
import com.example.budgetbuddy.repository.dao.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Transaction.class, Budget.class, LineItem.class}, version = 8)
@TypeConverters({LocalDateConverter.class, MonthYearConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao   userDao();

    public abstract BudgetDao budgetDao();

    public abstract TransactionDao transactionDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "budget_buddy")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
