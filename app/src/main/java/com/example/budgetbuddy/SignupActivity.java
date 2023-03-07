package com.example.budgetbuddy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.budgetbuddy.model.User;
import com.example.budgetbuddy.repository.dao.UserDao;
import com.example.budgetbuddy.servicelocator.ServiceLocator;
import com.example.budgetbuddy.ui.login.LoginActivity;
import com.example.budgetbuddy.utils.PasswordHelper;

import static android.content.ContentValues.TAG;

public class SignupActivity extends BaseActivity {
   private EditText email;
    private EditText userName;
    private EditText password;
    private EditText confirmPassword;
    private Button btnRegister;
    private TextView logInLink;
    private UserDao userDao;


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

        userDao = ServiceLocator.getInstance().getUserDao(getApplicationContext());

        btnRegister.setOnClickListener(v -> {
            registerUser();
            clearTextFields();
            startActivity( new Intent(this,LoginActivity.class));
        });

    logInLink.setOnClickListener(v ->
        startActivity( new Intent(this,LoginActivity.class)));

    }

    private void initView() {

        userName = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTxtEmail);
        password = findViewById(R.id.editTextPwd);
        confirmPassword = findViewById(R.id.editTxtConfirmPwd);
        btnRegister = findViewById(R.id.btn_Reset);
        logInLink = findViewById(R.id.txtViewSignIn);
    }

    private void clearTextFields(){
        userName.setText(null);
        email.setText(null);
        password.setText(null);
        confirmPassword.setText(null);
    }
    private boolean userExists(String email) {
        try {
            return userDao.findByEmail(email) != null;
        } catch (Exception e) {
            Log.e(TAG, "userExists: " + e.getMessage(), e);
            return true;
        }

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
        if (userExists(email.getText().toString().trim())) {
            Toast.makeText(this, "User already Exists", Toast.LENGTH_SHORT).show();
            return;
        }


        User user = new User(userName.getText().toString().trim(), email.getText().toString().trim(), PasswordHelper.hashedPassword(password.getText().toString().trim()));
        userDao.insert(user);
    }


    }




