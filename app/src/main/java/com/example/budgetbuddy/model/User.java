package com.example.budgetbuddy.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"username"},unique = true)})
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String username;
    public String password;
}
