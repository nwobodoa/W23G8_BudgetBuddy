package com.example.budgetbuddy.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.budgetbuddy.constants.Constants;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table in DB
        db.execSQL(Constants.CREATE_INCOME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //upgrade

        //drop table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_INCOME);
        //create table again
        onCreate(db);
    }

    //insert into DB
    public long insertIncome(int income, int incomeDate){
        //get writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.COLUMN_AMOUNT_INCOME, income);
        contentValues.put(Constants.COLUMN_DATE_INCOME, incomeDate);


        //insert date to DB | This returns the id for the record
        long id = db.insert(Constants.TABLE_INCOME, null, contentValues);

        //close db
        db.close();

        return id;

    }

//    public boolean isDataInserted() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM Constants.DATABASE_NAME";
//        Cursor cursor = db.rawQuery(query, null);
//        boolean isInserted = cursor.moveToFirst();
//        cursor.close();
//        db.close();
//        return isInserted;
//    }

}
