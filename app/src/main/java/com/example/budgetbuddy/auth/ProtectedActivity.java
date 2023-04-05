package com.example.budgetbuddy.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetbuddy.data.LoginRepository;
import com.example.budgetbuddy.ui.login.LoginActivity;

public abstract class ProtectedActivity extends AppCompatActivity {
    public void redirectToLoginIfNoUser(){
        if (!LoginRepository.getInstance().isLoggedIn()){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        redirectToLoginIfNoUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        redirectToLoginIfNoUser();
    }
}
