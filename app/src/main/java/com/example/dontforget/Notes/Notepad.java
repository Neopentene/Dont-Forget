package com.example.dontforget.Notes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dontforget.R;
import com.example.dontforget.SchemaDataQueries;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Notepad extends Fragment implements DeleteNoteInterface {
    Activity context;
    Context mContext;
    private ListView noteList;
    private TextView Empty;

    public Notepad(Activity context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        View view = inflater.inflate(R.layout.fragment_notepad, container, false);

        noteList = view.findViewById(R.id.NoteList);
        Empty = view.findViewById(R.id.EmptyNoteList);

        refreshList();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = view.findViewById(R.id.NoteFabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startReminder = new Intent();
                startReminder.setClass(context, SetNotes.class);
                startReminder.putExtra("id", R.id.notepad);
                startActivity(startReminder);
            }
        });
    }

    private void refreshList() {
        ArrayList<Note> notes = new SchemaDataQueries().getAllNotes(mContext);
        if (notes.isEmpty()) {
            Empty.setVisibility(View.VISIBLE);
        } else {
            Empty.setVisibility(View.GONE);
            noteList.setAdapter(new NotepadAdapter(context, notes, this));
            ((BaseAdapter) noteList.getAdapter()).notifyDataSetChanged();
        }
    }


    @Override
    public void ListEmpty(int size) {
        if (size == 0) {
            Empty.setVisibility(View.VISIBLE);
        } else {
            Empty.setVisibility(View.GONE);
        }
    }
}
