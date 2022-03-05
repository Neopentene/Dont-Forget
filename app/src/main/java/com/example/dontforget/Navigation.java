package com.example.dontforget;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.dontforget.Notes.Notepad;
import com.example.dontforget.Reminders.Reminder;
import com.google.android.material.navigation.NavigationView;

public class Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar action_bar;
    private DrawerLayout drawer;
    private NavigationView navigation_set;
    private int Id, Back = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navigation);
        navigation_set = findViewById(R.id.drawer_view);
        navigation_set.setNavigationItemSelectedListener(this);
        action_bar = findViewById(R.id.Toolbar);
        setSupportActionBar(action_bar);
        drawer = findViewById(R.id.Nav_layout);
        ActionBarDrawerToggle toggle_icon = new ActionBarDrawerToggle(this, drawer, action_bar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle_icon);
        toggle_icon.syncState();

        if (savedInstanceState == null && getIntent() == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.Container, new Reminder(this)).commit();
            navigation_set.setCheckedItem(R.id.reminder);
        }
        else {
            Intent intent = getIntent();
            Id = intent.getIntExtra("id", R.id.reminder);
            if (Id == R.id.reminder) {
                getSupportFragmentManager().beginTransaction().replace(R.id.Container, new Reminder(this)).commit();
            } else if (Id == R.id.notepad) {
                getSupportFragmentManager().beginTransaction().replace(R.id.Container, new Notepad(this)).commit();
            }
            navigation_set.setCheckedItem(Id);
        }
    }

    @Override
    protected void onResume() {
        overridePendingTransition(0, 0);
        if (Id == R.id.reminder) {
            getSupportFragmentManager().beginTransaction().replace(R.id.Container, new Reminder(this)).commit();
        } else if (Id == R.id.notepad) {
            getSupportFragmentManager().beginTransaction().replace(R.id.Container, new Notepad(this)).commit();
        }

        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Handler BackFunctions = new Handler();
            if (Back == 0) {
                Toast.makeText(getApplicationContext(), "Press Again to Exit", Toast.LENGTH_SHORT).show();
                Back++;
                BackFunctions.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (Back >= 0)
                            Back = 0;
                    }
                }, 3000);
            }
            else if (Back >= 1){
                Back++;
                finishAffinity();
                finish();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.reminder:
                getSupportFragmentManager().beginTransaction().replace(R.id.Container, new Reminder(this)).commit();
                break;
            case R.id.notepad:
                getSupportFragmentManager().beginTransaction().replace(R.id.Container, new Notepad(this)).commit();
                break;
                /*
            case R.id.diary:
                getSupportFragmentManager().beginTransaction().replace(R.id.Container, new Diary()).commit();
                break;
                */
                /*
            case R.id.buy_paid:
            case R.id.rate:
                break;
                */
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
