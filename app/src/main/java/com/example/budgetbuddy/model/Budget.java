package com.example.budgetbuddy.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

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
//@Entity
//public class Budget {
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//   private LocalDate createdAt;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public LocalDate getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDate createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(Double amount) {
//        this.amount = amount;
//    }
//
//    private Double amount;
//}
