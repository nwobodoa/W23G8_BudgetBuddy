package com.example.budgetbuddy.constants;

public class Constants {
    public static final String DATABASE_NAME = "mydatabase3.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_INCOME = "income_table";
    public static final String COLUMN_ID_INCOME = "id";
    public static final String COLUMN_AMOUNT_INCOME = "income";
    public static final String COLUMN_DATE_INCOME = "date";

    public static final String TABLE_NAME_EXPENSE = "expense_table";
    public static final String COLUMN_ID_EXPENSE = "id";
    public static final String COLUMN_AMOUNT_EXPENSE = "income";
    public static final String COLUMN_DATE_EXPENSE = "date";

//    public static String id = "id";
//    public static String income = "income";
//    public static String incomeDate = "incomeDate";

    public static final String CREATE_INCOME_TABLE = "CREATE TABLE " +TABLE_INCOME+ "("+COLUMN_ID_INCOME + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_AMOUNT_INCOME + " INTEGER, " +COLUMN_DATE_INCOME+ " INTEGER);";
//    public static final String CREATE_INCOME_TABLE = "CREATE TABLE " +TABLE_NAME_INCOME+ "("+id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + income + " INTEGER, " +incomeDate + " INTEGER);";

}
