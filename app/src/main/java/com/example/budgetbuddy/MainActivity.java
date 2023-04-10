package com.example.budgetbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.budgetbuddy.auth.ProtectedActivity;
import com.example.budgetbuddy.data.LoginRepository;
import com.example.budgetbuddy.databinding.ActivityMainBinding;
import com.example.budgetbuddy.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalTime;

public class MainActivity extends ProtectedActivity {

    TextView txtGreeting;
    TextView txtViewGreeting;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        txtViewGreeting = navigationView.getHeaderView(0).findViewById(R.id.txtViewGreeting);
        txtGreeting = findViewById(R.id.toolbar_title);


        navigationView
                .getMenu()
                .findItem(R.id.nav_logout)
                .setOnMenuItemClickListener(menuItem -> {
                    LoginRepository.getInstance().logout();
                    Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    return true;
                });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_addExpense,
                R.id.nav_addIncome,
                R.id.nav_addAllocation,
                R.id.nav_stats,
                R.id.nav_home,
                R.id.nav_addBudget,
                R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        displayGreeting(getIntent().getExtras().getString("username"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void displayGreeting(String name) {
        int timeOfDay = LocalTime.now().getHour();
        if (timeOfDay >= 0 && timeOfDay < 12) {
            txtGreeting.setText("Good Morning " + name + " !");
            txtViewGreeting.setText("Good Morning " + name + " !");

        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            txtGreeting.setText("Good Afternoon " + name + " !");
            txtViewGreeting.setText("Good Afternoon " + name + " !");
        } else {
            txtGreeting.setText("Good Evening " + name + " !");
            txtViewGreeting.setText("Good Evening " + name + " !");
        }
    }

}
