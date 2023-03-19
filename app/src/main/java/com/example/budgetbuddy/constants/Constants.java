package com.example.budgetbuddy.constants;

public class Constants {
    public static final String DATABASE_NAME = "sampledatabase.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_INCOME = "income_table";
    public static final String COLUMN_ID_INCOME = "id";
    public static final String COLUMN_AMOUNT_INCOME = "income";
    public static final String COLUMN_DATE_INCOME = "date";

    public static final String TABLE_NAME_EXPENSE = "expense_table";
    public static final String COLUMN_ID_EXPENSE = "id";
    public static final String COLUMN_AMOUNT_EXPENSE = "expense";
    public static final String COLUMN_DATE_EXPENSE = "date";

    public static final String CREATE_INCOME_TABLE = "CREATE TABLE " +TABLE_INCOME+ "("+COLUMN_ID_INCOME + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_AMOUNT_INCOME + " REAL NOT NULL, " +COLUMN_DATE_INCOME+ " TEXT NOT NULL);";

    //to be used in creating an expense table
    //    public static final String CREATE_EXPENSE_TABLE = "CREATE TABLE " +TABLE_NAME_EXPENSE+ "("+id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + expense + " INTEGER, " +expenseDate + " INTEGER);";

}
