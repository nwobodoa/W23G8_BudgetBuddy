package com.example.budgetbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.budgetbuddy.constants.Constants;
import com.example.budgetbuddy.utils.DatabaseHelper;

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
                  int incomeDate = Integer.parseInt(editTextDate.getText().toString());
                  long insertedId = databaseHelper.insertIncome(income, incomeDate);
                    try{
                        if (isDataInserted()) {
//                            Log.d("Data Inserted", "Data has been inserted into the database.");
                            Toast.makeText(AddIncome.this, "income added:" + insertedId, Toast.LENGTH_LONG).show();
                        } else {
//                            Log.d("Data Not Inserted", "No data has been inserted into the database yet.");
                            Toast.makeText(AddIncome.this, "income not added", Toast.LENGTH_LONG).show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(AddIncome.this, "Exception", Toast.LENGTH_SHORT).show();
                    }
              }else{
                  Toast.makeText(AddIncome.this, "Empty input", Toast.LENGTH_SHORT).show();
              }
            }
        });
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