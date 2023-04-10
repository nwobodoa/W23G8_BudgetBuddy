package com.example.budgetbuddy.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.ui.login.LoginActivity;
import com.example.budgetbuddy.utils.PasswordHelper;

public class SignupActivity extends AppCompatActivity {
    private final MutableLiveData<Long> userId = new MutableLiveData<>();
    private EditText userName;
    private EditText password;
    private EditText confirmPassword;
    private Button btnRegister;
    private TextView logInLink;
    private EditText email;
    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_white_logo);
        actionBar.setTitle(R.string.app_name);
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        btnRegister.setOnClickListener(v -> registerUser());
        logInLink.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
    }

    private void initView() {
        userName = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTxtEmail);
        password = findViewById(R.id.editTextPwd);
        confirmPassword = findViewById(R.id.editTxtConfirmPwd);
        btnRegister = findViewById(R.id.btn_Reset);
        logInLink = findViewById(R.id.txtViewSignIn);
    }

    private void clearTextFields() {
        userName.setText(null);
        email.setText(null);
        password.setText(null);
        confirmPassword.setText(null);
    }


    private void registerUser() {
        if (password.getText().toString().trim().length() < 5) {
            Toast.makeText(this, "password too short!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
            Toast.makeText(this, "Password mismatch!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.getText().toString().isBlank()) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userName.getText().toString().isBlank()) {
            Toast.makeText(this, "please enter your Username", Toast.LENGTH_SHORT).show();
            return;
        }


        signupViewModel.addNewUser(userName.getText().toString().trim(),
                        email.getText().toString().trim(),
                        PasswordHelper.hashedPassword(password.getText().toString().trim()))
                .observe(this, signUpState -> {
            Toast.makeText(this, signUpState.toString(), Toast.LENGTH_SHORT).show();
            clearTextFields();
            if (signUpState == SignUpState.SUCCESS) {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });
    }
}




