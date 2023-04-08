package com.example.budgetbuddy.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.budgetbuddy.model.LineItem;

import java.time.YearMonth;
import java.util.List;

@Dao
public interface LineItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(LineItem lineItem);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<LineItem> lineItems);

    @Query("SELECT * FROM LineItem WHERE budgetId = :budgetId")
    LiveData<List<LineItem>> getLineItems(Long budgetId);
}
