package com.example.budgetbuddy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class Expense {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public double value;

    public String description;
    @ColumnInfo(name = "expense_date")
    public LocalDate expenseDate;

    public Expense(double value, String description, LocalDate expenseDate) {
        this.value = value;
        this.description = description;
        this.expenseDate = expenseDate;
    }

    public double getExpense() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getExpenseDate() {
        return this.expenseDate;
    }
}
