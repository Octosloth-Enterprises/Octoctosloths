package com.example.octosloths;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

// BASED ON TUTORIAL: https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118

public class AddNoteActivityAward extends AppCompatActivity { // for the adding note page
    // ui inputs
    // hours is automatic
    // start date is automatic
    private EditText editTextTitle;
    private EditText editTextDescription;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private DatePicker datePickerEnd;

    private Intent intent;

    // entry type to pass back to main
    public static String ENTRY_TYPE = "AWARD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_award);

        // ui for displaying and selecting date
        // start date
       mDisplayDate = (TextView) findViewById(R.id.textView8_award);
       mDisplayDate.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.N)
           @Override
           public void onClick(View v) {
               // sets default date to current date
               Calendar cal = Calendar.getInstance();
               int year = cal.get(Calendar.YEAR);
               int month = cal.get(Calendar.MONTH); 
               int day = cal.get(Calendar.DAY_OF_MONTH);

               // shows new datepicker
               DatePickerDialog dialog = new DatePickerDialog(AddNoteActivityAward.this,
                       android.R.style.Theme_Holo_Dialog_MinWidth,mDateSetListener,year,month,day);
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               dialog.show();
           }
       });

       mDateSetListener = new DatePickerDialog.OnDateSetListener() { // when date has been set

           @Override
           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               month = month + 1;
               String date = month + "/" + dayOfMonth + "/" + year; // displaying date to user
               mDisplayDate.setText(date);
               Toast.makeText(AddNoteActivityAward.this, "set display text 1 to: " + mDisplayDate.getId(), Toast.LENGTH_SHORT).show();

                // assigns current view to start date
               datePickerEnd = view;
           }
       };





        // similar to what we've been doing in the past
        editTextTitle = findViewById(R.id.edit_text_title_award);
        editTextDescription = findViewById(R.id.edit_text_description_award);

        Log.v("AddNoteActivityAward: ", "onCreate entered");

        // getting menu bar and setting icons
        // setting toolbar and close icon to the left
        Toolbar toolbar = findViewById(R.id.toolbar_add_note_award);
        setSupportActionBar(toolbar); // setting toolbar as action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        // intent is a class variable
        intent = getIntent(); // getting intent that started this activity


        if(intent.hasExtra(MainActivity.EXTRA_ID)) { // we only sent id for editing
            setTitle("Edit Award Entry");

            // setting data
            editTextTitle.setText(intent.getStringExtra(MainActivity.EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(MainActivity.EXTRA_DESCRIPTION));
            mDisplayDate.setText(intent.getStringExtra(MainActivity.EXTRA_START_DATE));


        }
        else {
            setTitle("Add Award Entry");
        }


    }

    // method to save note
    private void saveNote() {
        // getting value of ui fields
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        String hrs = "0"; // hardcoded bc no hours for award
        String gpa = "0.0"; // hardcoded bc no gpa for award

        // checking fields are not empty
        if(title.trim().isEmpty() || description.trim().isEmpty()) {
            // show toast message
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        String endDateStr = mDisplayDate.getText().toString();
        if(endDateStr.isEmpty()) {
            Calendar calEnd = Calendar.getInstance();
            int yearEnd = calEnd.get(Calendar.YEAR);
            int monthEnd = calEnd.get(Calendar.MONTH) + 1;
            int dayEnd = calEnd.get(Calendar.DAY_OF_MONTH);

            endDateStr = "" + monthEnd + "/" + dayEnd + "/" + yearEnd;
        }
        String startDateStr = endDateStr; // startdate is automatically same as end date

        // sending data back to main activity, not matter add or update
        Intent data = new Intent();
        data.putExtra(MainActivity.EXTRA_TITLE, title);
        data.putExtra(MainActivity.EXTRA_DESCRIPTION, description);
        data.putExtra(MainActivity.EXTRA_HOURS, hrs);
        data.putExtra(MainActivity.EXTRA_START_DATE, startDateStr); // start date sent over with an extra
        data.putExtra(MainActivity.EXTRA_END_DATE, endDateStr);
        data.putExtra(MainActivity.EXTRA_GPA, gpa);
        data.putExtra(MainActivity.EXTRA_ENTRY_TYPE, ENTRY_TYPE);


        // for update situation
        int id = getIntent().getIntExtra(MainActivity.EXTRA_ID, -1);
        if(id != -1) {
            data.putExtra(MainActivity.EXTRA_ID, id); // only put the id in result intent if it is an update situation
        }

        setResult(RESULT_OK, data); // can read later in main to check if everything went as planned
        finish(); // closes this activity, when saved this page will disappear
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu); // tells system to use add note menu as the menu of this activity
        // is this a conflict with the toolbar?
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // param item is the item that is clicked

        switch(item.getItemId()) { // switch for one case
            case android.R.id.home:
                finish(); // hopefully this closes the activity
            case R.id.save_note:
                saveNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}