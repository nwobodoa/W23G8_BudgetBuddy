package com.example.budgetbuddy.ui.signup;

import androidx.annotation.NonNull;

public enum SignUpState {
    SUCCESS("User added successfully"),
    ERROR("An error occurred while adding a user"),
    DUPLICATE("User exists");

    private final String message;

    SignUpState(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return this.message;
    }

}
