package com.example.budgetbuddy.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.Set;

public class BudgetWithLineItems {
    @Embedded
    private Budget budget;

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Set<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(Set<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @Relation(
            parentColumn = "id",
            entityColumn = "budgetId"
    )
    private Set<LineItem> lineItems;

    public BudgetWithLineItems(Budget budget, Set<LineItem> lineItems) {
        this.budget = budget;
        this.lineItems = lineItems;
    }
}
