package com.example.budgetbuddy.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Budget {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public double value;

    public Budget(double value) {
        this.value = value;
    }

    public double getBudget() {
        return this.value;
    }
}
