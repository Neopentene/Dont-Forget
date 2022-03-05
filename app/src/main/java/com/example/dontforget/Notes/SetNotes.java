package com.example.dontforget.Notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dontforget.Navigation;
import com.example.dontforget.R;
import com.example.dontforget.SchemaDataQueries;
import com.google.android.material.textfield.TextInputLayout;

public class SetNotes extends AppCompatActivity {

    private TextInputLayout subject, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notes);

        Toolbar toolbar = findViewById(R.id.ToolbarNotes);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Notes");

        subject = findViewById(R.id.SubjectLayoutNotes);
        description = findViewById(R.id.DescriptionLayoutNotes);
    }

    public void AddNotesOnClick(View view) {
        Note note = new Note(subject.getEditText().getText().toString().trim().isEmpty() ? "None" : subject.getEditText().getText().toString().trim()
                , description.getEditText().getText().toString().trim().isEmpty() ? "None" : description.getEditText().getText().toString().trim());
        new SchemaDataQueries().addNotes(note, this);
        Intent pass = new Intent(this, Navigation.class);
        pass.putExtra("id", getIntent().getIntExtra("id", R.id.notepad));
        pass.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(pass);
        Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}