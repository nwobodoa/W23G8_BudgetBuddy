package com.example.budgetbuddy.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LineItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double amount;
    private Category category;

    private String description;

    private long budgetId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
