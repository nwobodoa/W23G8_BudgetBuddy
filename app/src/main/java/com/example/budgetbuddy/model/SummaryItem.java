package com.example.budgetbuddy.model;

public class SummaryItem {
    private Category category;
    private Double budgetAmount;
    private Double ActualSpend;

    public SummaryItem(Category category, Double budgetAmount, Double actualSpend) {
        this.category = category;
        this.budgetAmount = budgetAmount;
        ActualSpend = actualSpend;
    }

    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public Double getActualSpend() {
        return ActualSpend;
    }

    public void setActualSpend(Double actualSpend) {
        ActualSpend = actualSpend;
    }
}
