package com.example.budgetbuddy;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.budgetbuddy.data.LoginDataSource;
import com.example.budgetbuddy.data.LoginRepository;
import com.example.budgetbuddy.ui.login.LoginActivity;

@RequiresApi(Build.VERSION_CODES.O)
public abstract class ProtectedActivity extends AppCompatActivity {
    private LoginRepository loginRepository;
    public void redirectToLoginIfNoUser(){
        if (!loginRepository.isLoggedIn()){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
    @Override
    protected   void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginRepository = LoginRepository.getInstance(new LoginDataSource());
        redirectToLoginIfNoUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        redirectToLoginIfNoUser();
    }
}
