package com.example.budgetbuddy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btnSignIn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_white_logo);
        actionBar.setTitle(R.string.app_name);

//        btn_signIn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try{
//                    Intent i = new Intent(LoginActivity.this, AddIncome.class);
//                    startActivity(i);
//                }catch (Exception e){
//                    Toast toast = Toast.makeText(LoginActivity.this, "Hello, world!", Toast.LENGTH_SHORT);
//                    toast.show();
//                }
//            }
//        });

        btnSignIn2 = findViewById(R.id.btn_signIn2);

        btnSignIn2.setOnClickListener(view -> {
            Intent addIncome = new Intent(LoginActivity.this, AddIncome.class);
            startActivity(addIncome);
        });
    }
}