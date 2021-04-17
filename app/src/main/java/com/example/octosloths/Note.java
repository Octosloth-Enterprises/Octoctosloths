package com.example.octosloths;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table") // represent table in architecture
public class Note { // data model

    @PrimaryKey(autoGenerate = true) // autogenerating a key/id for instance of note, very cool annotations
    private int id; // key for each entry in table

    // desired fields for note in table
    private String title;

    private String description;

    private int prio;

    public Note(String title, String description, int prio) {
        this.title = title;
        this.description = description;
        this.prio = prio;
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

    public int getPrio() {
        return prio;
    }

}
