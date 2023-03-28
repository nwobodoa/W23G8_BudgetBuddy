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

    public String category;
    @ColumnInfo(name = "expense_date")
    public LocalDate expenseDate;

    public Expense(double value, String category, LocalDate expenseDate) {
        this.value = value;
        this.category = category;
        this.expenseDate = expenseDate;
    }

    public double getExpense() {
        return this.value;
    }

    public String getCategory() {
        return this.category;
    }

    public LocalDate getExpenseDate() {
        return this.expenseDate;
    }
}
