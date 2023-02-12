package com.example.budgetbuddy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnSignUp;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_white_logo);
        actionBar.setTitle(R.string.app_name);

        btnLogin = findViewById(R.id.login_btn);
        btnSignUp = findViewById(R.id.sign_up_btn);

        btnLogin.setOnClickListener(view -> {
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
        });

        btnSignUp.setOnClickListener(view -> {
            Intent signUp = new Intent(MainActivity.this,SignupActivity.class);
            startActivity(signUp);
        });
    }

}