package com.example.budgetbuddy.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetbuddy.MainActivity;
import com.example.budgetbuddy.PasswordReset;
import com.example.budgetbuddy.R;
import com.example.budgetbuddy.SignupActivity;
import com.example.budgetbuddy.databinding.ActivityLoginBinding;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher_white_logo);
        actionBar.setTitle(R.string.app_name);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText emailEditText = binding.editTxtEmail;
        final EditText passwordEditText = binding.editTextPwd;
        final Button loginButton = binding.btnSignIn2;
        final TextView linkSignup = binding.txtViewSignupLink;
        final TextView pwdResetLink = binding.txtViewForgotPwd;

        pwdResetLink.setOnClickListener(v ->
            startActivity(new Intent(this, PasswordReset.class)));


        linkSignup.setOnClickListener(v ->
            startActivity(new Intent(this, SignupActivity.class)));


        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                emailEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }

            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);
            Intent intent = new Intent(this, MainActivity.class);
            String name = loginResult.getSuccess().getDisplayName();
            intent.putExtra("username",name);
            startActivity(intent);
            //Complete and destroy login activity once successful
            finish();
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(emailEditText.getText().toString(),
                        passwordEditText.getText().toString(), getApplicationContext());
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            loginViewModel.login(emailEditText.getText().toString(),
             passwordEditText.getText().toString(),getApplicationContext());
            emailEditText.setText(null);
            passwordEditText.setText(null);
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String name = model.getDisplayName();
        String welcome = getString(R.string.welcome) + name;
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();



    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}