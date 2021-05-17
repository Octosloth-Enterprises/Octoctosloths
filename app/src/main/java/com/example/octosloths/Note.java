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

    private Calendar startDate;
    private Calendar endDate;

    private double gpa;

    private String entryType;


    public Note(String title, String description, int hours, Calendar startDate, Calendar endDate, double gpa, String entryType) { // using calendar almost like wrapper class
        this.title = title;
        this.description = description;
        this.hours = hours;
        this.startDate = startDate;
        this.endDate = endDate;
        this.gpa = gpa;
        this.entryType = entryType;
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

    public double getGpa() { return gpa; }

    public String getEntryType() { return entryType; }

    // to make display easier
    public String getStartDateStr() {
        return startDate.get(Calendar.MONTH) + "/"
                + startDate.get(Calendar.DAY_OF_MONTH) + "/"
                + startDate.get(Calendar.YEAR);
    }

    public String getEndDateStr() {
        return endDate.get(Calendar.MONTH) + "/"
                + endDate.get(Calendar.DAY_OF_MONTH) + "/"
                + endDate.get(Calendar.YEAR);
    }

}
