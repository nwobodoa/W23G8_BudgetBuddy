package com.example.budgetbuddy.model;


import androidx.annotation.NonNull;
import androidx.room.util.StringUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Category {
    INCOME("income"),
    DINING_OUT("dining out"),
    SHOPPING("dining out"),
    TRAVEL("travel spending"),
    CASH("cash"),
    HOME("home"),
    HEALTH("health"),
    ENTERTAINMENT("entertainment"),
    EDUCATION("education"),
    UTILITIES("utilities"),

    MISCELLANEOUS("Miscellaneous");


    private static final Map<String, Category> BY_LABEL = new HashMap<>();

    static {
        Arrays.stream(values()).forEach(category -> BY_LABEL.put(category.label, category));
    }

    private final String label;

    Category(String label) {
        this.label = label;
    }


    public static Category valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    @NonNull
    @Override
    public  String toString(){
        if(label.length() <= 1){
            return label;
        }
        return label.toLowerCase().substring(0,1).toUpperCase() + label.substring(1).toLowerCase();
    }
}
