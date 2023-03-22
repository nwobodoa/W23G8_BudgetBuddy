package com.example.budgetbuddy.model;

import androidx.room.ColumnInfo;

import java.time.LocalDate;

public class Transaction {
    public int id;
    public double amount;
    public String description;

    @ColumnInfo(name = "transaction_date")
    public LocalDate transactionDate;

    public Transaction() {
    }
    public Transaction(double amount, String description, LocalDate transactionDate) {
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
