package com.example.octosloths;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// BASED ON TUTORIAL: https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> { // class to communicate note data to ui

    private List<Note> notes = new ArrayList<Note>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()) // getting an itemView
                .inflate(R.layout.note_item, parent, false); // standard recyclerview stuff apparently

        return new NoteHolder(itemView);
    }

    @Override
    // method for getting the data into the ui components, ex title into textview title, desc into textviewdesc
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position); // getting current note

        holder.textViewTitle.setText(currentNote.getTitle()); // setting title
        holder.textViewDescription.setText(currentNote.getDescription()); // setting desc
        holder.textViewPriority.setText(String.valueOf(currentNote.getPrio())); // setting prio
        // holder.textViewHours.setText(String.valueOf(currentNote.getHours())); // setting hours



    }

    @Override
    // get how many notes we want to display
    public int getItemCount() {
        return notes.size(); // returns size of notes
    }

    // set notes
    public void setNotes(List<Note> notes) { // getting list of nodes from backend into recyclerview
        this.notes = notes;
        notifyDataSetChanged(); // not v efficient
    }


    // getting a note at a pos
    public Note getNoteAt(int position) {
        return notes.get(position);
    }


    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private TextView textViewHours;

        public NoteHolder(View view) { // view is the whole cardview
            super(view);
            textViewTitle = view.findViewById(R.id.text_view_title);
            textViewDescription = view.findViewById(R.id.text_view_description);
            textViewPriority = view.findViewById(R.id.text_view_priority);
            // textViewHours = view.findViewById(R.id.text_view_hours);

            // edit screen when click on cardview
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // get pos of item

                    if(listener != null && position != RecyclerView.NO_POSITION) { // if not null and position is valid
                        listener.onItemClick(notes.get(position)); // cardview clicked, also passing node
                    }

                }
            });
        }
    }


    // for clicking an entry to edit, first need a click listener
    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    // setting onitemclicklistener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
