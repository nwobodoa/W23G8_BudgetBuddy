package com.example.budgetbuddy.converter;

import android.os.Build;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @TypeConverter
    public static LocalDate fromString(String date) {
            return date == null ? null : LocalDate.parse(date,formatter);
        }
    @TypeConverter
    public static String fromLocalDate(LocalDate date) {
        return date == null ? null : date.format(formatter);
    }
}

