package com.example.budgetbuddy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.budgetbuddy.ui.login.LoginActivity;

public class PasswordReset extends AppCompatActivity {
    EditText editTextEmail;
    Button btnResetPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_white_logo);
        actionBar.setTitle(R.string.app_name);

        editTextEmail = findViewById(R.id.editTxtEmail);
        btnResetPwd = findViewById(R.id.btn_Reset);

        btnResetPwd.setOnClickListener(v -> {
            if(editTextEmail.getText().toString().isEmpty()){
                Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            //TODO complete logic.
            String email = editTextEmail.getText().toString();
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}