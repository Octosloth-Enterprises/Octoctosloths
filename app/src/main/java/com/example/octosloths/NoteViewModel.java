package com.example.octosloths;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel { // communication bt ui and backend
    // viewmodel should not hold references to ui views, its meant to outlive the ui and potential changes

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) { // super constructor
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    // database operation methods for our notedatabase
    // very similar to noterepo and notedatabase and notedao
    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
