package com.example.budgetbuddy.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.budgetbuddy.utils.PasswordHelper;

@Entity(indices = {@Index(value = {"email"},unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String username;
    public String email;
    public String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
