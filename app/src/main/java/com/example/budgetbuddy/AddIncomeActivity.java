package com.example.budgetbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.budgetbuddy.model.Income;
import com.example.budgetbuddy.repository.AppDatabase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddIncomeActivity extends AppCompatActivity {

    EditText editTextIncome;
    EditText editTextDate;
    Button btnAddIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        editTextIncome = findViewById(R.id.editTextIncome);
        editTextDate = findViewById(R.id.editTextDate);
        btnAddIncome = findViewById(R.id.btnAddIncome);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "budget_buddy").build();

        btnAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextIncome.getText().toString().isBlank()) {
                    Toast.makeText(AddIncomeActivity.this, "Income cannot be empty, enter a valid Integer", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editTextDate.getText().toString().isBlank()) {
                    Toast.makeText(AddIncomeActivity.this, "Data cannot be empty, enter a valid date", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double income = Double.parseDouble(editTextIncome.getText().toString());
                    String incomeDate = editTextDate.getText().toString();
                    //TODO Please use futures to handle work that needs to leave the main UI thread
                    new Thread(() -> db
                            .incomeDao()
                            .insertAll(new Income(income, LocalDate.parse(incomeDate, DateTimeFormatter.ofPattern("dd/M/yyyy")))))
                            .start();

                    Toast.makeText(AddIncomeActivity.this, "Income added successfully", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AddIncomeActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                }
            }

        });
        editTextDate.setOnClickListener(v -> showDatePickerDialog());

    }

    private void showDatePickerDialog() {
        // Get the current date for the date picker's initial value
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            // Set the selected date in the EditText view
            String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            editTextDate.setText(selectedDate);
        },
                year, month, day);
        // Show the date picker dialog
        datePickerDialog.show();
    }

}