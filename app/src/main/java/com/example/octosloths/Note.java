package com.example.octosloths;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Calendar;
import java.util.Date;

// BASED ON TUTORIAL: https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118

@Entity(tableName = "note_table") // represent table in architecture

public class Note { // data model

    @PrimaryKey(autoGenerate = true) // autogenerating a key/id for instance of note, very cool annotations
    private int id; // key for each entry in table

    // desired fields for note in table
    private String title;

    private String description;

    // for volunteering and extracurriculars
    private int hours;

    /*
    public class CalendarConverter { // is this the right place to put this converter class? the error that I got traced to here
        @TypeConverter
        public Calendar storedStringToCal(String s) {
            String[] arr = s.split(","); // comma separated calendar

            int month = Integer.parseInt(arr[0]);
            int day = Integer.parseInt(arr[1]);
            int year = Integer.parseInt(arr[2]);

            Calendar cal = Calendar.getInstance(); // constructing calendar for today
            cal.set(year, month, day); // setting actual attributes for calendar
            return cal;
        }

        @TypeConverter
        public String calToStoredString(Calendar cal) {
            return cal.get(Calendar.MONTH) + ","
                    + cal.get(Calendar.DAY_OF_MONTH) + ","
                    + cal.get(Calendar.YEAR); // string with all fields concatenated with commas
        }
    } */

    // for start and end date of the activity or experience
    private Calendar startDate;
    private Calendar endDate;


    public Note(String title, String description, int hours, Calendar startDate, Calendar endDate) { // using calendar almost like wrapper class
        this.title = title;
        this.description = description;
        this.hours = hours;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    // to automatically create getter methods,
    // 1) right click anywhere in this file
    // 2) generate
    // 3) getter
    // 4) select all fields
    // 5) OK
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getHours() {
        return hours;
    }

    public Calendar getStartDate() { return startDate; }

    public Calendar getEndDate() { return endDate; }

}
