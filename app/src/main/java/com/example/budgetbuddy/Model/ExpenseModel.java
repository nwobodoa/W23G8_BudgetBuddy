package com.example.budgetbuddy.Model;

public class ExpenseModel {
    private int id;
    private int expense;
    private String expenseDate;

    public ExpenseModel() {
    }
    public ExpenseModel(int expense, String expenseDate) {
        this.expense = expense;
        this.expenseDate = expenseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }
}
