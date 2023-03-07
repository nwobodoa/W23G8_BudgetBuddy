package com.example.budgetbuddy.repository.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.budgetbuddy.model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM USER where email = :email")
    User findByEmail(String email);
    @Insert
    void insert(User user);
}
