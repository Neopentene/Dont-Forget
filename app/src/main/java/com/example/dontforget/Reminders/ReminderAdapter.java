package com.example.dontforget.Reminders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
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

public class ReminderAdapter extends ArrayAdapter<ReminderObject> {
    private Activity context;
    private ArrayList<ReminderObject> reminders;
    DeleteReminderInterface deleteReminderInterface;

    public ReminderAdapter(Activity context, ArrayList<ReminderObject> reminders, Reminder mContext) {
        super(context, R.layout.reminder_adapter_layout, reminders);
        this.context = context;
        this.reminders = reminders;
        deleteReminderInterface = mContext;
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

        titleTextView.setText(reminders.get(position).Title);
        descriptionTextView.setText(reminders.get(position).Description);

        deleteReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SchemaDataQueries().deleteReminder(context, reminders.get(position).id);
                reminders.remove(position);

                deleteReminderInterface.ListEmpty(getList().size());

                notifyDataSetChanged();
            }
        });

        moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.dialog_reminder, null);

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

                TextView Date = view.findViewById(R.id.TimeTextViewDialog);
                Date.setText(getList().get(position).Date);
                TextView Time = view.findViewById(R.id.DateTextViewDialog);
                Time.setText(getList().get(position).Time);

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

    public ArrayList<ReminderObject> getList() {
        return reminders;
    }
}