package com.example.budgetbuddy.ui.add_expense;

import androidx.annotation.Nullable;

public class ExpenseFormState {
    @Nullable
    private final Integer expenseError;

    @Nullable
    public Integer getExpenseError() {
        return expenseError;
    }

    @Nullable
    public Integer getDescriptionError() {
        return descriptionError;
    }

    @Nullable
    public Integer getCategoryError() {
        return categoryError;
    }

    @Nullable
    private final Integer descriptionError;

    @Nullable
    private final Integer categoryError;

    @Nullable
    private final Integer expenseDateError;

    @Nullable
    public Integer getExpenseDateError() {
        return expenseDateError;
    }

    private final boolean isDataValid;

    public ExpenseFormState(@Nullable Integer expenseError,
                            @Nullable Integer descriptionError,
                            @Nullable Integer categoryError,
                            @Nullable Integer expenseDateError,
                            boolean isDataValid) {
        this.expenseError = expenseError;
        this.descriptionError = descriptionError;
        this.categoryError = categoryError;
        this.expenseDateError = expenseDateError;
        this.isDataValid = isDataValid;

    }

    public ExpenseFormState(boolean isDataValid) {
        this.expenseError = null;
        this.descriptionError = null;
        this.categoryError = null;
        this.expenseDateError = null;
        this.isDataValid = isDataValid;
    }


    public boolean isDataValid() {
        return isDataValid;
    }
}
