package com.example.budgetbuddy.model;


import androidx.annotation.NonNull;

import com.example.budgetbuddy.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Category {
    INCOME("income", R.drawable.cash_wave_solid),
    DINING_OUT("dining out", R.drawable.utensils_solid),
    SHOPPING("shopping", R.drawable.basket_shopping_solid),
    TRAVEL("travel spending", R.drawable.bus_simple_solid),
    CASH("cash", R.drawable.cash_wave_solid),
    HOME("home", R.drawable.house_solid),
    HEALTH("health", R.drawable.medical_solid),
    ENTERTAINMENT("entertainment", R.drawable.film_solid),
    EDUCATION("education", R.drawable.education_solid),
    UTILITIES("utilities", R.drawable.utilities_solid),

    MISCELLANEOUS("Miscellaneous", R.drawable.dice_solid);


    private static final Map<String, Category> BY_LABEL = new HashMap<>();

    static {
        Arrays.stream(values()).forEach(category -> BY_LABEL.put(category.label, category));
    }

    private final String label;
    private final int picture;

    Category(String label, int picture) {
        this.label = label;
        this.picture = picture;
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

    public int getDrawableId() {
        return picture;
    }

    public static List<Category> getAllCategories() {
        return new ArrayList<>(BY_LABEL.values())
                .stream()
                .filter(category -> category != Category.INCOME)
                .sorted((Comparator.comparing(Category::toString)))
                .collect(Collectors.toList());
    }
}
