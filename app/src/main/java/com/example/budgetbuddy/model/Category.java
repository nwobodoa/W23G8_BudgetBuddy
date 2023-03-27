package com.example.budgetbuddy.model;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Category {
INCOME("income");

private static final Map<String,Category> BY_LABEL = new HashMap<>();

static  {
    Arrays.stream(values()).forEach(category -> BY_LABEL.put(category.label,category));
}
private final String label;
    Category(String income) {
        label = income;
    }

    public static Category valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
