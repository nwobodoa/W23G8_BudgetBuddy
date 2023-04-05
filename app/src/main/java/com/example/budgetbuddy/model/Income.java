package com.example.budgetbuddy.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class Income {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double value;

    private String description;

    private LocalDate createdAt;

    public Income(double value, String description, LocalDate payDate) {
        this.value = value;
        this.description = description;
        this.createdAt = payDate;
    }
}
