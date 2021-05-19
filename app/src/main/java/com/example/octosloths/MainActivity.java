package com.example.octosloths;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import androidx.lifecycle.Observer; // previous version deprecated, mapping for androidx, android.arch.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

// BASED ON TUTORIAL: https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118
//NAVBAR: https://www.youtube.com/watch?v=fGcMLu1GJEc, https://www.youtube.com/watch?v=zYVEMCiDcmY&t=24s, https://www.youtube.com/watch?v=bjYstsO1PgI&t=2s

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{ // hola amogus
    private DrawerLayout drawer;
    com.google.android.material.floatingactionbutton.FloatingActionButton button;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    public static final int CHANGE_PAGE_REQUEST = 3;

    // keys for intent, communication with main activity when saving note
    public static final String EXTRA_ID =
            "com.example.octosloths.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.octosloths.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.octosloths.EXTRA_DESCRIPTION";
    // public static final String EXTRA_PRIORITY  =
    // "com.example.octosloths.EXTRA_PRIORITY";
    public static final String EXTRA_HOURS  =
            "com.example.octosloths.EXTRA_HOURS";
    public static final String EXTRA_START_DATE  =
            "com.example.octosloths.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE  =
            "com.example.octosloths.EXTRA_END_DATE";
    public static final String EXTRA_GPA  =
            "com.example.octosloths.EXTRA_GPA";

    // for which type of entry they're entering
    /*public static final String EXTRA_BASIC =
            "com.example.octosloths.EXTRA_BASIC";
    public static final String EXTRA_VOLUNTEERING =
            "com.example.octosloths.EXTRA_VOLUNTEERING";
    public static final String EXTRA_AWARD =
            "com.example.octosloths.EXTRA_AWARD";
    public static final String EXTRA_EDUCATION =
            "com.example.octosloths.EXTRA_EDUCATION";
    public static final String EXTRA_EXTRACURRICULAR =
            "com.example.octosloths.EXTRA_EXTRACURRICULAR";*/

    // for entry type
    public static final String EXTRA_ENTRY_TYPE =
            "com.example.octosloths.EXTRA_ENTRY_TYPE";

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("MainActivity: ", "onCreate entered");
        super.onCreate(savedInstanceState);
        Log.v("MainActivity: ", "super.onCreate");
        setContentView(R.layout.activity_main);
        Log.v("MainActivity: ", "set content view");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // setting toolbar as action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // drawerToggle = setupDrawerToggle();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); //rotates hamburger icon with drawer

        // set an onlick listener for the hamburger
        drawer.addDrawerListener(toggle);
        /*toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        /*
        if (savedInstanceState == null) {
            //opens Basic Entries fragment when activity is started
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new BasicEntryFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_basicentry);
        }*/

        // add button that will bring up the addnote page
        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() { // setting onclicklistener, when clicked will open new page
            @Override
            public void onClick(View v) {

                // will retrieve input in method onactivityresult

            PopupMenu popupMenu = new PopupMenu(MainActivity.this, buttonAddNote);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    int id = item.getItemId();

                    if (id == R.id.one) { // basic

                        Intent intent1 = new Intent(MainActivity.this, AddNoteActivityBasic.class);
                        startActivityForResult(intent1, ADD_NOTE_REQUEST); // method to start activity and get input back
                        return false;
                    }
                    if (id == R.id.two) { // volunteering

                        Intent intent2 = new Intent(MainActivity.this, AddNoteActivityVolunteering.class);
                        startActivityForResult(intent2, ADD_NOTE_REQUEST); // method to start activity and get input back
                        return false;
                    }
                    if (id == R.id.three) { // extracurricular

                        Intent intent2 = new Intent(MainActivity.this, AddNoteActivityExtracurricular.class);
                        startActivityForResult(intent2, ADD_NOTE_REQUEST); // method to start activity and get input back
                        return false;
                    }
                    if (id == R.id.four) { // education

                        Intent intent2 = new Intent(MainActivity.this, AddNoteActivityEducation.class);
                        startActivityForResult(intent2, ADD_NOTE_REQUEST); // method to start activity and get input back
                        return false;
                    }
                    if (id == R.id.five) { // award

                        Intent intent2 = new Intent(MainActivity.this, AddNoteActivityAward.class);
                        startActivityForResult(intent2, ADD_NOTE_REQUEST); // method to start activity and get input back
                        return false;
                    }
                    return MainActivity.super.onOptionsItemSelected(item);
                }
            });
                popupMenu.show();

            }

        });
        Log.v("MainActivity: ", "onClick set");


        // recycler view and ui communication
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager); // layout manager for card view display
        recyclerView.setHasFixedSize(true); // recyclerview size won't change

        NoteAdapter noteAdapter = new NoteAdapter(); // do i need a final keyword here?

        recyclerView.setAdapter(noteAdapter);


        // is lifecycle-aware, so passing this will prevent mem leaks or crashes
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class); // changed from tutorial code since ViewModelProviders is deprecated, taking system suggestion to use ViewModelProvider constructor
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {

            @Override
            public void onChanged(@Nullable List<Note> notes) {
                // update recycler view
                noteAdapter.setNotes(notes);
            }
        });


        // swipe to delete functionality
        new ItemTouchHelper((new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) { // supporting left and right dirs
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // unchanged
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition())); // deleting note at adapter pos
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        })).attachToRecyclerView(recyclerView); // must attach else will not work


        // handling the click here
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                // sending over note data to addnoteactivity
                // lots of duplicate code, can definitely optimize
                if (note.getEntryType().equals("BASIC")) {
                    Intent intent = new Intent(MainActivity.this, AddNoteActivityBasic.class); // saying that it cannot resolve addeditnoteactivity, oh well, i'm assuming naming conventions don't matter

                    Log.v("MainActivity: ", "entry type: " + note.getEntryType());
                    Log.v("MainActivity: ", "note is null: "+ (note == null));

                    intent.putExtra(EXTRA_ID, note.getId());

                    intent.putExtra(EXTRA_TITLE, note.getTitle());
                    intent.putExtra(EXTRA_DESCRIPTION, note.getDescription());

                    // parsing calendar data, sending over via intent
                    Calendar startDate = note.getStartDate();
                    Calendar endDate = note.getEndDate();

                    int d1 = startDate.get(Calendar.DAY_OF_MONTH);
                    int m1 = startDate.get(Calendar.MONTH);
                    int y1 = startDate.get(Calendar.YEAR);

                    int d2 = endDate.get(Calendar.DAY_OF_MONTH);
                    int m2 = endDate.get(Calendar.MONTH);
                    int y2 = endDate.get(Calendar.YEAR);

                    String startDateStr = m1+"/"+d1+"/"+y1;
                    String endDateStr = m2+"/"+d2+"/"+y2;

                    // for debugging purposes
                    Toast.makeText(MainActivity.this, "startDateInt: "+startDateStr, Toast.LENGTH_SHORT).show();

                    // putting the start and end dates as string extras
                    intent.putExtra(EXTRA_START_DATE, startDateStr);
                    intent.putExtra(EXTRA_END_DATE, endDateStr);

                    // starting activity
                    startActivityForResult(intent, EDIT_NOTE_REQUEST); }

                else if (note.getEntryType().equals("VOLUNTEERING")) {
                    Intent intent2 = new Intent(MainActivity.this, AddNoteActivityVolunteering.class); // saying that it cannot resolve addeditnoteactivity, oh well, i'm assuming naming conventions don't matter

                    intent2.putExtra(EXTRA_ID, note.getId());

                    intent2.putExtra(EXTRA_TITLE, note.getTitle());
                    intent2.putExtra(EXTRA_DESCRIPTION, note.getDescription());
                    intent2.putExtra(EXTRA_HOURS, note.getHours() + ""); // sus

                    // parsing calendar data, sending over via intent
                    Calendar startDateV = note.getStartDate();
                    Calendar endDateV = note.getEndDate();

                    int d1V = startDateV.get(Calendar.DAY_OF_MONTH);
                    int m1V = startDateV.get(Calendar.MONTH);
                    int y1V = startDateV.get(Calendar.YEAR);

                    int d2V = endDateV.get(Calendar.DAY_OF_MONTH);
                    int m2V = endDateV.get(Calendar.MONTH);
                    int y2V = endDateV.get(Calendar.YEAR);

                    String startDateStrV = m1V+"/"+d1V+"/"+y1V;
                    String endDateStrV = m2V+"/"+d2V+"/"+y2V;

                    // putting the start and end dates as string extras
                    intent2.putExtra(EXTRA_START_DATE, startDateStrV);
                    intent2.putExtra(EXTRA_END_DATE, endDateStrV);

                    // starting activity
                    startActivityForResult(intent2, EDIT_NOTE_REQUEST); }

                else if(note.getEntryType().equals("EXTRACURRICULAR")) {
                    Intent intent3 = new Intent(MainActivity.this, AddNoteActivityExtracurricular.class); // saying that it cannot resolve addeditnoteactivity, oh well, i'm assuming naming conventions don't matter

                    intent3.putExtra(EXTRA_ID, note.getId());

                    intent3.putExtra(EXTRA_TITLE, note.getTitle());
                    intent3.putExtra(EXTRA_DESCRIPTION, note.getDescription());

                    // parsing calendar data, sending over via intent
                    Calendar startDateEx = note.getStartDate();
                    Calendar endDateEx = note.getEndDate();

                    int d1Ex = startDateEx.get(Calendar.DAY_OF_MONTH);
                    int m1Ex = startDateEx.get(Calendar.MONTH);
                    int y1Ex = startDateEx.get(Calendar.YEAR);

                    int d2Ex = endDateEx.get(Calendar.DAY_OF_MONTH);
                    int m2Ex = endDateEx.get(Calendar.MONTH);
                    int y2Ex = endDateEx.get(Calendar.YEAR);

                    String startDateStrEx = m1Ex+"/"+d1Ex+"/"+y1Ex;
                    String endDateStrEx = m2Ex+"/"+d2Ex+"/"+y2Ex;

                    // putting the start and end dates as string extras
                    intent3.putExtra(EXTRA_START_DATE, startDateStrEx);
                    intent3.putExtra(EXTRA_END_DATE, endDateStrEx);

                    // starting activity
                    startActivityForResult(intent3, EDIT_NOTE_REQUEST); }

                else if(note.getEntryType().equals("EDUCATION")){
                    Intent intent4 = new Intent(MainActivity.this, AddNoteActivityEducation.class); // saying that it cannot resolve addeditnoteactivity, oh well, i'm assuming naming conventions don't matter

                    intent4.putExtra(EXTRA_ID, note.getId());

                    intent4.putExtra(EXTRA_TITLE, note.getTitle());
                    intent4.putExtra(EXTRA_DESCRIPTION, note.getDescription());
                    intent4.putExtra(EXTRA_GPA, note.getGpa() + ""); // sus

                    // parsing calendar data, sending over via intent
                    Calendar startDateEd = note.getStartDate();
                    Calendar endDateEd = note.getEndDate();

                    int d1Ed = startDateEd.get(Calendar.DAY_OF_MONTH);
                    int m1Ed = startDateEd.get(Calendar.MONTH);
                    int y1Ed = startDateEd.get(Calendar.YEAR);

                    int d2Ed = endDateEd.get(Calendar.DAY_OF_MONTH);
                    int m2Ed = endDateEd.get(Calendar.MONTH);
                    int y2Ed = endDateEd.get(Calendar.YEAR);

                    String startDateStrEd = m1Ed+"/"+d1Ed+"/"+y1Ed;
                    String endDateStrEd = m2Ed+"/"+d2Ed+"/"+y2Ed;

                    // putting the start and end dates as string extras
                    intent4.putExtra(EXTRA_START_DATE, startDateStrEd);
                    intent4.putExtra(EXTRA_END_DATE, endDateStrEd);

                    // starting activity
                    startActivityForResult(intent4, EDIT_NOTE_REQUEST);}

                else if(note.getEntryType().equals("AWARD")){
                    Intent intent5 = new Intent(MainActivity.this, AddNoteActivityAward.class); // saying that it cannot resolve addeditnoteactivity, oh well, i'm assuming naming conventions don't matter

                    intent5.putExtra(EXTRA_ID, note.getId());

                    intent5.putExtra(EXTRA_TITLE, note.getTitle());
                    intent5.putExtra(EXTRA_DESCRIPTION, note.getDescription());

                    // parsing calendar data, sending over via intent
                    Calendar startDateA = note.getStartDate();
                    Calendar endDateA = note.getEndDate();

                    int d1A = startDateA.get(Calendar.DAY_OF_MONTH);
                    int m1A = startDateA.get(Calendar.MONTH);
                    int y1A = startDateA.get(Calendar.YEAR);

                    int d2A = endDateA.get(Calendar.DAY_OF_MONTH);
                    int m2A = endDateA.get(Calendar.MONTH);
                    int y2A = endDateA.get(Calendar.YEAR);

                    String startDateStrA = m1A+"/"+d1A+"/"+y1A;
                    String endDateStrA = m2A+"/"+d2A+"/"+y2A;

                    // putting the start and end dates as string extras
                    intent5.putExtra(EXTRA_START_DATE, startDateStrA);
                    intent5.putExtra(EXTRA_END_DATE, endDateStrA);

                    // starting activity
                    startActivityForResult(intent5, EDIT_NOTE_REQUEST);
                }
            }
        });
    }

    @Override
    // getting data from intent.result from addnoteactivity
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) { // if the codes are the same
            // getting data
            String title = data.getStringExtra(EXTRA_TITLE);
            String description = data.getStringExtra(EXTRA_DESCRIPTION);
            String hours = data.getStringExtra(EXTRA_HOURS); // passing back as string
            int hrs = Integer.parseInt(hours);

            String startDate = data.getStringExtra(EXTRA_START_DATE);
            String endDate = data.getStringExtra(EXTRA_END_DATE);
            double gpa = Double.parseDouble(data.getStringExtra(EXTRA_GPA));
            String entryType = data.getStringExtra(EXTRA_ENTRY_TYPE);


            // start date calendar object
            String[] startDateStr = startDate.split("/"); // getting the textview to string
            int m1 = Integer.parseInt(startDateStr[0]);
            int d1 = Integer.parseInt(startDateStr[1]);
            int y1 = Integer.parseInt(startDateStr[2]);

            Calendar sDate = Calendar.getInstance(); // constructing calendar for today
            sDate.set(y1, m1, d1); // setting actual attributes for calendar


            // end date calendar object
            String[] endDateStr = endDate.split("/");
            int m2 = Integer.parseInt(endDateStr[0]);
            int d2 = Integer.parseInt(endDateStr[1]);
            int y2 = Integer.parseInt(endDateStr[2]);

            Calendar eDate = Calendar.getInstance(); // constructing calendar for today
            eDate.set(y2, m2, d2); // setting actual attributes for calendar

            // int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);

            // placeholder date for passing to constructor
            // Calendar cal = Calendar.getInstance();

            // creating new note and inserting in database
            Note note = new Note(title, description, hrs, sDate, eDate, gpa, entryType); // will change cal
            noteViewModel.insert(note);

            // toast for check
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) { // for edit situation
            int id = data.getIntExtra(EXTRA_ID, -1);


            if(id == -1) { // if for some reason invalid id
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            // same as above
            String title = data.getStringExtra(EXTRA_TITLE);
            String description = data.getStringExtra(EXTRA_DESCRIPTION);
            String hours = data.getStringExtra(EXTRA_HOURS); // passing back as string

            String startDate = data.getStringExtra(EXTRA_START_DATE);
            String endDate = data.getStringExtra(EXTRA_END_DATE);
            double gpa = Double.parseDouble(data.getStringExtra(EXTRA_GPA));
            String entryType = data.getStringExtra(EXTRA_ENTRY_TYPE);

            int hrs = Integer.parseInt(hours);

            // start date calendar object
            String[] startDateStr = startDate.split("/"); // getting the textview to string
            int m1 = Integer.parseInt(startDateStr[0]);
            int d1 = Integer.parseInt(startDateStr[1]);
            int y1 = Integer.parseInt(startDateStr[2]);

            Calendar sDate = Calendar.getInstance(); // constructing calendar for today
            sDate.set(y1, m1, d1); // setting actual attributes for calendar


            // end date calendar object
            String[] endDateStr = endDate.split("/");
            int m2 = Integer.parseInt(endDateStr[0]);
            int d2 = Integer.parseInt(endDateStr[1]);
            int y2 = Integer.parseInt(endDateStr[2]);

            Calendar eDate = Calendar.getInstance(); // constructing calendar for today
            eDate.set(y2, m2, d2); // setting actual attributes for calendar

            // int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);

            // placeholder date for passing to constructor
            // Calendar cal = Calendar.getInstance();

            // same as above
            Note note = new Note(title, description, hrs, sDate, eDate, gpa, entryType); // will change
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        }
        else { // if we leave the activity via the back button
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu); // will make this menu the menu of this activity, also in add note activity
        return true;
    }*/


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // same as in addnoteactivity
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {// opens fragments
        switch (item.getItemId()) {
            case R.id.nav_basicentry: //opens Basic Entries fragment
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        // new BasicEntryFragment()).commit();

                // start a new intent for displaying all the basic notes in a new layout
                // main activity is analogous to all notes, but we can call it "home"
                Intent intent1 = new Intent(MainActivity.this, DisplayActivityBasic.class);
                startActivityForResult(intent1, CHANGE_PAGE_REQUEST);
                break;

            case R.id.nav_awardentry: //opens Award Entries fragment
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        // new AwardEntryFragment()).commit();

                Intent intent2 = new Intent(MainActivity.this, DisplayActivityAward.class);
                startActivityForResult(intent2, CHANGE_PAGE_REQUEST);

                break;

            case R.id.nav_educationentry: //opens Education Entries fragment
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        // new EducationEntryFragment()).commit();

                Intent intent3 = new Intent(MainActivity.this, DisplayActivityEducation.class);
                startActivityForResult(intent3, CHANGE_PAGE_REQUEST);

                break;

            case R.id.nav_extracurricularentry: //opens Extracurricular Entries fragment
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        // new ExtracurricularEntryFragment()).commit();

                Intent intent4 = new Intent(MainActivity.this, DisplayActivityExtracurricular.class);
                startActivityForResult(intent4, CHANGE_PAGE_REQUEST);

                break;
            case R.id.nav_volunteerentry: //opens Volunteer Entries fragment
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        // new VolunteerEntryFragment()).commit();

                Intent intent5 = new Intent(MainActivity.this, DisplayActivityVolunteering.class);
                startActivityForResult(intent5, CHANGE_PAGE_REQUEST);

                break;

            case R.id.nav_home: //opens mainactivity

                Intent intent6 = new Intent(MainActivity.this, MainActivity.class);
                startActivityForResult(intent6, CHANGE_PAGE_REQUEST);

                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() { //back button should close navigation drawer, not leave activity
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}


