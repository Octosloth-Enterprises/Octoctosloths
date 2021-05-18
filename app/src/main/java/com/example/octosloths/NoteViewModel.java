package com.example.octosloths;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

// BASED ON TUTORIAL: https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118

public class NoteViewModel extends AndroidViewModel { // communication bt ui and backend
    // viewmodel should not hold references to ui views, its meant to outlive the ui and potential changes

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Note>> allBasicNotes;

    private LiveData<List<Note>> allVolunteeringNotes;
    private LiveData<List<Note>> allEducationNotes;
    private LiveData<List<Note>> allExtracurricularNotes;
    private LiveData<List<Note>> allAwardNotes;


    public NoteViewModel(@NonNull Application application) { // super constructor
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
        allBasicNotes = repository.getAllBasicNotes(); // layer of abstraction, repository is actually getting from notedao

        allVolunteeringNotes = repository.getAllVolunteeringNotes();
        allEducationNotes = repository.getAllEducationNotes();
        allExtracurricularNotes = repository.getAllExtracurricularNotes();
        allAwardNotes = repository.getAllAwardNotes();
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

    public LiveData<List<Note>> getAllBasicNotes() {
        return allBasicNotes;
    }

    public LiveData<List<Note>> getAllVolunteeringNotes() {
        return allVolunteeringNotes;
    }

    public LiveData<List<Note>> getAllEducationNotes() {
        return allEducationNotes;
    }

    public LiveData<List<Note>> getAllExtracurricularNotes() {
        return allExtracurricularNotes;
    }

    public LiveData<List<Note>> getAllAwardNotes() {
        return allAwardNotes;
    }
}
