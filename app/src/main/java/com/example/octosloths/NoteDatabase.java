package com.example.octosloths;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// BASED ON TUTORIAL: https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase { // database of notes, backend

    // declaring vars and methods, not exactly sure the significance of each
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) { // synchronized = only 1 thread at a time can access this method, will not instance 2 instances

        // if instance not yet instantiated
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), // context
                    NoteDatabase.class, "note_database") // this class, the name of this file
                    .fallbackToDestructiveMigration() // migrating versions (line 9)
                    .addCallback(roomCallback) // calling back, onstart, when instance first created, will populate database
                    .build(); // building instance
        }

        return instance;
    }

    // onstart... for some reason using callback, not exactly sure what that is
    private static Callback roomCallback = new Callback() { // static since getInstance is static
        @Override // press ctrl + o for override methods
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute(); // autopopulating
        }
    };

    // class for autopopulating
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao notedao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            notedao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) { // inserting new notes to automatically populate ui on start
            notedao.insert(new Note("title 1", "desc 1", 1));
            notedao.insert(new Note("title 2", "desc 2", 2));
            notedao.insert(new Note("title 3", "desc 3", 3));

            return null;
        }


    }
}
