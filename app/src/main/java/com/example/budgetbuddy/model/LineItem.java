package com.example.budgetbuddy.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LineItem {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private double amount;
    private Category category;

    public LineItem(double amount, Category category) {
        this.amount = amount;
        this.category = category;
    }

    public LineItem(@Nullable Long id,@Nullable Long budgetId, double amount, Category category) {
        this.amount = amount;
        this.category = category;
        this.budgetId = budgetId;
        this.id = id;
    }

    public  LineItem() {

    }

    @Nullable
    private Long budgetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nullable
    public Long getBudgetId() {
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
}
