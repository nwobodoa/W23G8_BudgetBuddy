package com.example.budgetbuddy.converter;

import androidx.room.TypeConverter;
import java.time.YearMonth;

public class MonthYearConverter {
    @TypeConverter
    public static YearMonth fromString(String yearMonth) {
        return yearMonth == null ? null : YearMonth.parse(yearMonth);
    }
    @TypeConverter
    public static String fromYearMonth(YearMonth yearMonth) {
        return yearMonth == null ? null : yearMonth.toString();
    }
}
