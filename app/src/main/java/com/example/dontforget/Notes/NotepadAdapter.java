package com.example.dontforget.Notes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dontforget.R;
import com.example.dontforget.SchemaDataQueries;

import java.util.ArrayList;

public class NotepadAdapter extends ArrayAdapter<Note> {
    private Activity context;
    private ArrayList<Note> notes;
    DeleteNoteInterface deleteNoteInterface;

    public NotepadAdapter(@NonNull Activity context, ArrayList<Note> notes, Notepad mContext) {
        super(context, R.layout.note_adapter_layout , notes);
        this.notes = notes;
        this.context = context;
        deleteNoteInterface = mContext;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") final View view = inflater.inflate(R.layout.reminder_adapter_layout, parent, false);

        TextView titleTextView = view.findViewById(R.id.TitleTextView);
        TextView descriptionTextView = view.findViewById(R.id.DescriptionTextView);
        final ImageButton deleteReminder = view.findViewById(R.id.DeleteReminder);
        ImageButton moreDetails = view.findViewById(R.id.MoreDetails);

        titleTextView.setText(notes.get(position).Title);
        descriptionTextView.setText(notes.get(position).Description);

        deleteReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SchemaDataQueries().deleteNote(context, notes.get(position).id);
                notes.remove(position);

                deleteNoteInterface.ListEmpty(getList().size());

                notifyDataSetChanged();
            }
        });

        moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.dialog_reminder, null);
                TextView Dummy;
                View one, two;
                int[] views = {R.id.DateDialog, R.id.TimeDialog, R.id.TimeTextViewDialog, R.id.DateTextViewDialog};

                ImageButton button = view.findViewById(R.id.OkReminderDialog);

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setView(view);
                final AlertDialog alert = builder.create();
                alert.getWindow().setBackgroundDrawableResource(R.drawable.dialog_transparent);
                alert.show();

                TextView Title = view.findViewById(R.id.TitleTextViewDialog);
                Title.setText(getList().get(position).Title);

                TextView Description = view.findViewById(R.id.DescriptionTextViewDialog);
                Description.setText(getList().get(position).Description);

                one = view.findViewById(R.id.ReminderDividerDialogOne);
                two = view.findViewById(R.id.ReminderDividerDialogTwo);
                one.setVisibility(View.GONE);
                two.setVisibility(View.GONE);

                for (int i : views) {
                    Dummy = view.findViewById(i);
                    Dummy.setVisibility(View.GONE);
                }

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.dismiss();
                    }
                });
            }
        });

        return view;
    }

    public ArrayList<Note> getList(){
        return notes;
    }
}
