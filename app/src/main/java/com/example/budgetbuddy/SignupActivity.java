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

public class SignupActivity extends AppCompatActivity {
    private final AppCompatActivity activity = SignupActivity.this;
    private NestedScrollView nestedScrollView;
    private EditText email;
    private EditText userName;
    private EditText password;
    private EditText confirmPassword;
    private Button btnRegister;
    private TextView logInLink;
    //    private InputValidation inputValidation;
//    private DatabaseHelper databaseHelper;
    private User user;
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
        //nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
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

        user = new User(userName.getText().toString().trim(), email.getText().toString().trim(), PasswordHelper.hashedPassword(password.getText().toString().trim()));
        userDao.insert(user);
    }

    private void postDataToSQLite() {
//        if (!inputValidation.isInputEditTextFilled(userName, textInputLayoutName, getString(R.id.error_message_name))) {
//            return;
//        }
//        if (!inputValidation.isInputEditTextFilled(email, textInputLayoutEmail, getString(R.id.error_message_email))) {
//            return;
//        }
//        if (!inputValidation.isInputEditTextEmail(userName, textInputLayoutName, getString(R.id.error_message_name))) {
//            return;
//        }
//        if (!inputValidation.isInputEditTextMatches(password, confirmPassword, textInputLayoutConfirmPassword, getString(R.id.error_password_match))) {
//            return;
//        }
//        if (!databaseHelper.checkUser(email.getText().toString().trim())) {
//            user.setName(userName.getText().toString().trim());
//            user.setEmail(email.getText().toString().trim());
//            user.setPassword(password.getText().toString().trim());
//
//            databaseHelper.addUser(user);
//            //snack Bar to show success message that record saved successfully
//            Snackbar.make(nestedScrollView, getString(R.string.sucess_message), Snackbar.LENGTH_LONG).show();
//            emptyEditText();
//        } else {
//            // snack Bar to show success message that record saved sucessfully
//            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
//
//        }

    }


}

