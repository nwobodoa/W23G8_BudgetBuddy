package com.example.budgetbuddy.ui.budget;

public class Budget {
    private int budgetId;
    private String budgetName;
    private int budgetPic;
    private double budgetAmount;

    public Budget(int budgetId, String budgetName, int budgetPic, double budgetAmount) {
        this.budgetId = budgetId;
        this.budgetName = budgetName;
        this.budgetPic = budgetPic;
        this.budgetAmount = budgetAmount;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public int getBudgetPic() {
        return budgetPic;
    }

    public void setBudgetPic(int budgetPic) {
        this.budgetPic = budgetPic;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }
}
