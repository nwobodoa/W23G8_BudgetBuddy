package com.example.budgetbuddy.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.Set;

public class AllocationWithLineItems {
    @Embedded
    private Allocation allocation;

    public Allocation getAllocation() {
        return allocation;
    }

    public void setAllocation(Allocation allocation) {
        this.allocation = allocation;
    }

    public Set<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(Set<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    @Relation(
            parentColumn = "id",
            entityColumn = "allocationId"
    )
    private Set<LineItem> lineItems;

    public AllocationWithLineItems(Allocation allocation, Set<LineItem> lineItems) {
        this.allocation = allocation;
        this.lineItems = lineItems;
    }
}
