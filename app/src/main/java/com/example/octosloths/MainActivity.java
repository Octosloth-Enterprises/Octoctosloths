package com.example.octosloths;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.List;
import java.util.Observable;
import androidx.lifecycle.Observer; // previous version deprecated, mapping for androidx, android.arch.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

// BASED ON TUTORIAL: https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118

public class MainActivity extends AppCompatActivity { // hola

     com.google.android.material.floatingactionbutton.FloatingActionButton button;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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

                    if (id == R.id.one) {

                        Intent intent1 = new Intent(MainActivity.this, AddNoteActivity.class);
                        startActivityForResult(intent1, ADD_NOTE_REQUEST); // method to start activity and get input back
                        return false;
                    }
                    if (id == R.id.two) {

                        Intent intent2 = new Intent(MainActivity.this, AddNoteActivity.class);
                        startActivityForResult(intent2, ADD_NOTE_REQUEST); // method to start activity and get input back
                        return false;
                    }
                    return MainActivity.super.onOptionsItemSelected(item);
                }
            });
                popupMenu.show();

            }

        });


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
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class); // saying that it cannot resolve addeditnoteactivity, oh well, i'm assuming naming conventions don't matter

                Log.v("MainActivity: ", "cardview has been clicked");
                Log.v("MainActivity: ", "note is null: "+ (note == null));

                intent.putExtra(AddNoteActivity.EXTRA_ID, note.getId());

                intent.putExtra(AddNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddNoteActivity.EXTRA_PRIORITY, note.getPrio());

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
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);

            // creating new note and inserting in database
            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);

            // toast for check
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) { // for edit situation
            int id = data.getIntExtra(AddNoteActivity.EXTRA_ID, -1);


            if(id == -1) { // if for some reason invalid id
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }


            // same as above
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);

            // same as above
            Note note = new Note(title, description, priority);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        }
        else { // if we leave the activity via the back button
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu); // will make this menu the menu of this activity, also in add note activity
        return true;
    }


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
}