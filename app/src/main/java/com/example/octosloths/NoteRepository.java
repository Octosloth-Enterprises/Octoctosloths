package com.example.octosloths;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

// BASED ON TUTORIAL: https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118

public class NoteRepository { // abstraction between viewmodel and backend
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Note>> allBasicNotes;

    public NoteRepository(Application app) {
        NoteDatabase database = NoteDatabase.getInstance(app);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
        allBasicNotes = noteDao.getAllBasicNotes();
    }

    // these methods are the methods that are exposed to the outside, so we don't have to worry about the backend, abstraction layer
    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Note>> getAllBasicNotes() {
        return allBasicNotes;
    }


    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> { // execute the tasks above for database
        private NoteDao notedao;

        private InsertNoteAsyncTask(NoteDao notedao) {
            this.notedao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notedao.insert(notes[0]); // param acts similar to array
            return null;
        }


    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> { // execute the tasks above for database
        private NoteDao notedao;

        private UpdateNoteAsyncTask(NoteDao notedao) {
            this.notedao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notedao.update(notes[0]); // param acts similar to array
            return null;
        }


    }


    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> { // execute the tasks above for database
        private NoteDao notedao;

        private DeleteNoteAsyncTask(NoteDao notedao) {
            this.notedao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notedao.delete(notes[0]); // param acts similar to array
            return null;
        }


    }


    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> { // execute the tasks above for database
        private NoteDao notedao;

        private DeleteAllNoteAsyncTask(NoteDao notedao) {
            this.notedao = notedao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notedao.deleteAllNotes(); // param acts similar to array
            return null;
        }


    }
}
