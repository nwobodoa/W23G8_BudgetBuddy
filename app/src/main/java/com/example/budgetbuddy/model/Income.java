package com.example.budgetbuddy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class Income {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public double value;

    public String description;
    @ColumnInfo(name = "pay_date")
    public LocalDate payDate;

    public Income(double value, String description, LocalDate payDate) {
        this.value = value;
        this.description = description;
        this.payDate = payDate;
    }

    public double getIncome() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getIncomeDate() {
        return this.payDate;
    }
}
