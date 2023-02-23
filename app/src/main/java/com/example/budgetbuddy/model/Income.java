package com.example.budgetbuddy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class Income {
    public Income(double value, LocalDate payDate) {
        this.value = value;
        this.payDate = payDate;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;
    public double value;
    @ColumnInfo(name = "pay_date")
    public LocalDate payDate;
}
