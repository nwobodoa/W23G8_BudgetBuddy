package com.example.budgetbuddy;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.budgetbuddy.auth.ProtectedActivity;
import com.example.budgetbuddy.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalTime;

public class MainActivity extends ProtectedActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ImageView imgViewExpenseIcon;


    TextView txtViewGreeting;
    ImageView imgViewHome;
    ImageView imgViewIncome;
    ImageView imgViewExpense;
    ImageView imgViewBudget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);


        imgViewHome = binding.drawerLayout.findViewById(R.id.imgViewHomeIcon);
        imgViewIncome = binding.drawerLayout.findViewById(R.id.imgViewAddIncome);
        imgViewExpense = binding.drawerLayout.findViewById(R.id.imgViewExpenseIcon);
        imgViewBudget = binding.drawerLayout.findViewById(R.id.imgViewBudgettIcon);

        imgViewIncome.setOnClickListener(view -> {
            NavController navControllerIncome = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main2);
            navControllerIncome.navigateUp();
            navControllerIncome.navigate(R.id.nav_addIncome);
        });

        imgViewExpense.setOnClickListener(view -> {
            NavController navControllerIncome = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main2);
            navControllerIncome.navigateUp();
            navControllerIncome.navigate(R.id.nav_addExpense);
        });

        imgViewBudget.setOnClickListener(view -> {
            NavController navControllerIncome = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main2);
            navControllerIncome.navigateUp();
            navControllerIncome.navigate(R.id.nav_addBudget);
        });

        imgViewHome.setOnClickListener(view -> {
            NavController navControllerIncome = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main2);
            navControllerIncome.navigateUp();
            navControllerIncome.navigate(R.id.nav_home);
        });

       DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        txtViewGreeting =  navigationView.getHeaderView(0).findViewById(R.id.txtViewGreeting);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_addExpense, R.id.nav_addIncome, R.id.nav_addBudget, R.id.nav_spendingByCat, R.id.nav_spendingHistory,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        displayGreeting(getIntent().getExtras().getString("username"));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            int timeOfDay =  LocalTime.now().getHour();
            if (timeOfDay >= 0 && timeOfDay < 12) {

                txtViewGreeting.setText("Good Morning " + name + "!");

            } else if (timeOfDay >= 12 && timeOfDay < 16) {
                txtViewGreeting.setText("Good Afternoon " + name + "!");
            } else {
                txtViewGreeting.setText("Good Evening " + name + "!");
            }
    }

}
