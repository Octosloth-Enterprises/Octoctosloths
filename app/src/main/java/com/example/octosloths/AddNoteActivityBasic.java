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

import java.util.Calendar;

// BASED ON TUTORIAL: https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118

public class AddNoteActivityBasic extends AppCompatActivity { // for the adding note page

    // ui inputs
    private EditText editTextTitle;
    private EditText editTextDescription;

    private TextView mDisplayDate;
    private TextView mDisplayDate2;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;

    private DatePicker datePickerStart;
    private DatePicker datePickerEnd;

    public static String ENTRY_TYPE = "BASIC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_basic);

        // ui for displaying and selecting date
        // start date
       mDisplayDate = (TextView) findViewById(R.id.textView7_basic);
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
               DatePickerDialog dialog = new DatePickerDialog(AddNoteActivityBasic.this,
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
               Toast.makeText(AddNoteActivityBasic.this, "set display text 1 to: " + mDisplayDate.getId(), Toast.LENGTH_SHORT).show();

                // assigns current view to start date
               datePickerStart = view;
           }
       };


       // end date
        mDisplayDate2 = (TextView) findViewById(R.id.textView8_basic);
        mDisplayDate2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddNoteActivityBasic.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,mDateSetListener2,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                mDisplayDate2.setText(date);
                Toast.makeText(AddNoteActivityBasic.this, "set display text 2 to: " + mDisplayDate2.getId(), Toast.LENGTH_SHORT).show();

                datePickerEnd = view;
            }
        };





        // similar to what we've been doing in the past
        editTextTitle = findViewById(R.id.edit_text_title_basic);
        editTextDescription = findViewById(R.id.edit_text_description_basic);

        Log.v("AddNoteActivity: ", "onCreate entered");


        // getting menu bar and setting icons
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);



        Intent intent = getIntent(); // getting intent that started this activity


        if(intent.hasExtra(MainActivity.EXTRA_ID)) { // we only sent id for editing
            setTitle("Edit Basic Entry");

            // setting data
            editTextTitle.setText(intent.getStringExtra(MainActivity.EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(MainActivity.EXTRA_DESCRIPTION));
            mDisplayDate.setText(intent.getStringExtra(MainActivity.EXTRA_START_DATE));
            mDisplayDate2.setText(intent.getStringExtra(MainActivity.EXTRA_END_DATE));


        }
        else {
            setTitle("Add Basic Entry");
        }


    }

    // method to save note
    private void saveNote() {
        // getting value of ui fields
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        String hrs = "0";
        String gpa = "0.0";

        // checking fields are not empty
        if(title.trim().isEmpty() || description.trim().isEmpty()) {
            // show toast message
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        // parsing the date and seeing if date is empty, will default to the current date
        String startDateStr = mDisplayDate.getText().toString();
        if(startDateStr.isEmpty()) {
            Calendar calStart = Calendar.getInstance();
            int yearStart = calStart.get(Calendar.YEAR);
            int monthStart = calStart.get(Calendar.MONTH) + 1;
            int dayStart = calStart.get(Calendar.DAY_OF_MONTH);

            startDateStr = "" + monthStart + "/" + dayStart + "/" + yearStart;
        }

        String endDateStr = mDisplayDate2.getText().toString();
        if(endDateStr.isEmpty()) {
            Calendar calEnd = Calendar.getInstance();
            int yearEnd = calEnd.get(Calendar.YEAR);
            int monthEnd = calEnd.get(Calendar.MONTH) + 1;
            int dayEnd = calEnd.get(Calendar.DAY_OF_MONTH);

            endDateStr = "" + monthEnd + "/" + dayEnd + "/" + yearEnd;
        }

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

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // param item is the item that is clicked

        switch(item.getItemId()) { // switch for one case
            case R.id.save_note:
                saveNote();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}