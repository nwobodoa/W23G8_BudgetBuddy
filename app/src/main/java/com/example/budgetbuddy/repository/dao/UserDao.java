package com.example.budgetbuddy.repository.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.budgetbuddy.model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM USER where username = :username AND password = :password")
    User findByUsernameAndPassword(String username, String password);
}
