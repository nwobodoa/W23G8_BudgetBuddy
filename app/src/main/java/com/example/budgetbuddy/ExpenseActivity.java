package com.example.budgetbuddy;

import android.os.Bundle;

import com.example.budgetbuddy.auth.ProtectedActivity;

public class ExpenseActivity extends ProtectedActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
    }
}
