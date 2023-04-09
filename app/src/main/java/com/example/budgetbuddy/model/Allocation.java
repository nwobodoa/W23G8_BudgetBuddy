package com.example.budgetbuddy.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.YearMonth;

import java.time.LocalDate;

@Entity
public class Allocation {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private LocalDate createdAt;
    private YearMonth yearMonth;

    public Allocation() {

    }

    public Allocation(LocalDate createdAt, YearMonth yearMonth){
        this.createdAt = createdAt;
        this.yearMonth = yearMonth;
    }

    public Allocation(Long id, LocalDate createdAt, YearMonth yearMonth){
        this.createdAt = createdAt;
        this.yearMonth = yearMonth;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
