package com.example.budgetbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.budgetbuddy.constants.Constants;
import com.example.budgetbuddy.utils.DatabaseHelper;

import java.util.Date;

public class AddIncome extends AppCompatActivity {

    public DatabaseHelper databaseHelper = new DatabaseHelper(this);;
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

        btnAddIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(editTextIncome.getText().toString() != null && editTextDate.getText().toString() != null){

                  int income = Integer.parseInt(editTextIncome.getText().toString());
                  String incomeDate = editTextDate.getText().toString();
                  long insertedId = databaseHelper.insertIncome(income, incomeDate);
                    try{
                        if (isDataInserted()) {
                            Toast.makeText(AddIncome.this, "Income added successfully in row: " + insertedId, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AddIncome.this, "Error adding income", Toast.LENGTH_LONG).show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(AddIncome.this, "Exception", Toast.LENGTH_SHORT).show();
                    }
              }else{
                  Toast.makeText(AddIncome.this, "Empty/invalid input", Toast.LENGTH_SHORT).show();
              }
            }
        });


        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }

    private void showDatePickerDialog() {
        // Get the current date for the date picker's initial value
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set the selected date in the EditText view
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editTextDate.setText(selectedDate);
                    }
                },
                year, month, day);

        // Show the date picker dialog
        datePickerDialog.show();
    }


    public boolean isDataInserted() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + Constants.TABLE_INCOME;
        Cursor cursor = db.rawQuery(query, null);
        boolean isInserted = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isInserted;
    }


}