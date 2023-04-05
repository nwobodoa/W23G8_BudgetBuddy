package com.example.budgetbuddy.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.YearMonth;

import java.time.LocalDate;

@Entity
public class Budget {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private LocalDate createdAt;
    private YearMonth yearMonth;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

}
