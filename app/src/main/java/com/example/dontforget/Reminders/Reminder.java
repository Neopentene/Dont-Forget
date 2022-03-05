package com.example.dontforget.Reminders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dontforget.R;
import com.example.dontforget.SchemaDataQueries;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.VisibilityAwareImageButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Reminder extends Fragment implements DeleteReminderInterface {
    Activity context;

    public Reminder(Activity context) {
        this.context = context;
    }

    Context mContext;
    ListView reminderList;
    TextView Empty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();

        Context themeWrapper = new ContextThemeWrapper(getActivity(), R.style.MaterialComponents);
        LayoutInflater layoutInflater = inflater.cloneInContext(themeWrapper);
        View view = layoutInflater.inflate(R.layout.fragment_reminder, container, false);

        reminderList = view.findViewById(R.id.ReminderList);
        Empty = view.findViewById(R.id.EmptyList);

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
        FloatingActionButton fab = view.findViewById(R.id.fabButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startReminder = new Intent();
                startReminder.setClass(context, SetReminder.class);
                startReminder.putExtra("id", R.id.reminder);
                startActivity(startReminder);
            }
        });
    }

    private void refreshList(){
        ArrayList<ReminderObject> reminders = new SchemaDataQueries().getAllRemainder(mContext);
        if(reminders.isEmpty()){
            Empty.setVisibility(View.VISIBLE);
        }
        else {
            Empty.setVisibility(View.GONE);
            reminderList.setAdapter(new ReminderAdapter(context, reminders, this));
            ((BaseAdapter) reminderList.getAdapter()).notifyDataSetChanged();
        }
    }

    @Override
    public void ListEmpty(int size) {
        if(size == 0){
            Empty.setVisibility(View.VISIBLE);
        }
        else {
            Empty.setVisibility(View.GONE);
        }
    }
}
