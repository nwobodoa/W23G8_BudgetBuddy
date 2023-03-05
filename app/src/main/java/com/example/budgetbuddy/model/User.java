package com.example.budgetbuddy.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"email"},unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String username;
    public String email;
    public String password;
}
