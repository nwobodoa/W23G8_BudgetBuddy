package com.example.budgetbuddy.Model;

public class ExpenseModel {
    private int id;
    private int expense;
    private int expenseDate;

    public ExpenseModel() {
    }
    public ExpenseModel(int expense, int expenseDate) {
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

    public int getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(int expenseDate) {
        this.expenseDate = expenseDate;
    }
}
