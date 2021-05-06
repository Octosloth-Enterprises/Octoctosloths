package com.example.octosloths;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class CalendarConverter { // is this the right place to put this converter class? the error that I got traced to here
    @TypeConverter
    public Calendar storedStringToCal(String s) {
        String[] arr = s.split("/"); // comma separated calendar

        int month = Integer.parseInt(arr[0]);
        int day = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);

        Calendar cal = Calendar.getInstance(); // constructing calendar for today
        cal.set(year, month, day); // setting actual attributes for calendar
        return cal;
    }

    @TypeConverter
    public String calToStoredString(Calendar cal) {
        return cal.get(Calendar.MONTH) + "/"
                + cal.get(Calendar.DAY_OF_MONTH) + "/"
                + cal.get(Calendar.YEAR); // string with all fields concatenated with commas
    }
}
