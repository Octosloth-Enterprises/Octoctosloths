package com.example.octosloths;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.List;

// BASED ON TUTORIAL: https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118
//NAVBAR:

public class DisplayActivityBasic extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{ // hola amogus
    private DrawerLayout drawer;
    FloatingActionButton button;

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

    // for entry type
    public static final String EXTRA_ENTRY_TYPE =
            "com.example.octosloths.EXTRA_ENTRY_TYPE";

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_basic); // new xml, very similar to activity_main

        // setting title of page
        setTitle("Basic Entries");

        // setting toolbar for new xml
        Toolbar toolbar = findViewById(R.id.toolbar_display_basic);
        setSupportActionBar(toolbar); // setting toolbar as action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_display_basic);
        // drawerToggle = setupDrawerToggle();

        NavigationView navigationView = findViewById(R.id.nav_view_display_basic);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); //rotates hamburger icon with drawer

        // set an onlick listener for the hamburger
        drawer.addDrawerListener(toggle);


        // add button that will bring up the addnote page
        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note_display_basic);
        buttonAddNote.setOnClickListener(new View.OnClickListener() { // setting onclicklistener, when clicked will open new page
            @Override
            public void onClick(View v) {

                // will retrieve input in method onactivityresult

            PopupMenu popupMenu = new PopupMenu(DisplayActivityBasic.this, buttonAddNote);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    int id = item.getItemId();

                    if (id == R.id.one) { // basic

                        Intent intent1 = new Intent(DisplayActivityBasic.this, AddNoteActivityBasic.class);
                        startActivityForResult(intent1, ADD_NOTE_REQUEST); // method to start activity and get input back
                        return false;
                    }
                    if (id == R.id.two) { // volunteering

                        Intent intent2 = new Intent(DisplayActivityBasic.this, AddNoteActivityVolunteering.class);
                        startActivityForResult(intent2, ADD_NOTE_REQUEST); // method to start activity and get input back
                        return false;
                    }
                    if (id == R.id.three) { // extracurricular

                        Intent intent2 = new Intent(DisplayActivityBasic.this, AddNoteActivityExtracurricular.class);
                        startActivityForResult(intent2, ADD_NOTE_REQUEST); // method to start activity and get input back
                        return false;
                    }
                    if (id == R.id.four) { // education

                        Intent intent2 = new Intent(DisplayActivityBasic.this, AddNoteActivityEducation.class);
                        startActivityForResult(intent2, ADD_NOTE_REQUEST); // method to start activity and get input back
                        return false;
                    }
                    if (id == R.id.five) { // award

                        Intent intent2 = new Intent(DisplayActivityBasic.this, AddNoteActivityAward.class);
                        startActivityForResult(intent2, ADD_NOTE_REQUEST); // method to start activity and get input back
                        return false;
                    }
                    return DisplayActivityBasic.super.onOptionsItemSelected(item);
                }
            });
                popupMenu.show();

            }

        });
        Log.v("MainActivity: ", "onClick set");


        // recycler view and ui communication
        // this recycler view will contain just the basic cards
        RecyclerView recyclerView = findViewById(R.id.recycler_view_display_basic);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager); // layout manager for card view display
        recyclerView.setHasFixedSize(true); // recyclerview size won't change

        NoteAdapter noteAdapter = new NoteAdapter(); // do i need a final keyword here?

        recyclerView.setAdapter(noteAdapter);


        // is lifecycle-aware, so passing this will prevent mem leaks or crashes
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class); // changed from tutorial code since ViewModelProviders is deprecated, taking system suggestion to use ViewModelProvider constructor

        // instead all the notes get all the BASIC notes
        noteViewModel.getAllBasicNotes().observe(this, new Observer<List<Note>>() {

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
                Toast.makeText(DisplayActivityBasic.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        })).attachToRecyclerView(recyclerView); // must attach else will not work


        // handling the click here
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                // sending over note data to addnoteactivity
                Intent intent = new Intent(DisplayActivityBasic.this, AddNoteActivityBasic.class);

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

                // putting the start and end dates as string extras
                intent.putExtra(EXTRA_START_DATE, startDateStr);
                intent.putExtra(EXTRA_END_DATE, endDateStr);

                // starting activity
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
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

    // this disappears if we try to make the menu a toolbar, not sure how to get it back, not sure if we necessarily need it
    // toolbar is not in a separate xml, it's just in the activity_main, so I'm not sure as to how to add an item to the menu
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

    // when the drawer options are selected, where do they link and what do they do
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {// opens fragments
        switch (item.getItemId()) {
            case R.id.nav_basicentry: //opens Basic Entries fragment
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        // new BasicEntryFragment()).commit();

                // start a new intent for displaying all the basic notes in a new layout
                // main activity is analogous to all notes, but we can call it "home"
                Intent intent1 = new Intent(DisplayActivityBasic.this, AddNoteActivityBasic.class);
                startActivityForResult(intent1, ADD_NOTE_REQUEST);
                break;

            case R.id.nav_awardentry: //opens Award Entries fragment
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        // new AwardEntryFragment()).commit();

                Intent intent2 = new Intent(DisplayActivityBasic.this, AddNoteActivityAward.class);
                startActivityForResult(intent2, ADD_NOTE_REQUEST);

                break;

            case R.id.nav_educationentry: //opens Education Entries fragment
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        // new EducationEntryFragment()).commit();

                Intent intent3 = new Intent(DisplayActivityBasic.this, AddNoteActivityEducation.class);
                startActivityForResult(intent3, ADD_NOTE_REQUEST);

                break;

            case R.id.nav_extracurricularentry: //opens Extracurricular Entries fragment
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        // new ExtracurricularEntryFragment()).commit();

                Intent intent4 = new Intent(DisplayActivityBasic.this, AddNoteActivityExtracurricular.class);
                startActivityForResult(intent4, ADD_NOTE_REQUEST);

                break;
            case R.id.nav_volunteerentry: //opens Volunteer Entries fragment
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        // new VolunteerEntryFragment()).commit();

                Intent intent5 = new Intent(DisplayActivityBasic.this, AddNoteActivityVolunteering.class);
                startActivityForResult(intent5, ADD_NOTE_REQUEST);

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


