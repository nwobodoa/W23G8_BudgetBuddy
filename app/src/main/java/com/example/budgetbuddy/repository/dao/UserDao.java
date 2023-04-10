package com.example.budgetbuddy.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.budgetbuddy.model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM USER where email = :email")
    LiveData<User> findByEmail(String email);
    @Query("SELECT * FROM USER where email = :email")
    User getUserByEmail(String email);
    @Insert
    Long insert(User user);
}
