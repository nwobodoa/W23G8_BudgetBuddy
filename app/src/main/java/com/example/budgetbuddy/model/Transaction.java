package com.example.budgetbuddy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double amount;
    private Category category;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private LocalDate createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setCategory(String category) {
        this.category = Category.valueOfLabel(category);
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Transaction() {
    }
    public Transaction(double amount, String description,Category category, LocalDate transactionDate) {
        this.amount = amount;
        this.description = description;
        this.createdAt = transactionDate;
        this.category = category;
    }

}
