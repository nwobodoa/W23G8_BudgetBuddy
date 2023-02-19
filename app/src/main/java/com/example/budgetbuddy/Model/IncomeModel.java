package com.example.budgetbuddy.Model;

import java.sql.Date;

public class IncomeModel {
    private int id;
    private int income;
    private String incomeDate;

    public IncomeModel() {
    }

    public IncomeModel(int income, String incomeDate) {
        this.income = income;
        this.incomeDate = incomeDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }
}
