
package com.example.dontforget.Reminders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.example.dontforget.BroadCastNotification;
import com.example.dontforget.Navigation;
import com.example.dontforget.R;
import com.example.dontforget.SchemaDataQueries;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.GZIPInputStream;

public class SetReminder extends AppCompatActivity {
    private TextInputLayout subjectEditTextLayout, descriptionEditTextLayout;
    private String Subject;
    private NestedScrollView scroll;
    private TextView SubjectWayPoint, TimeWayPoint, SetTime, SetDate;
    private int NotEmptyUpdate = 0, TimeAddedUpdate = 0, TimeSet = 0;
    private String Date, Time;
    private Handler handler;
    private boolean ConditionSatisfied = false, NotEmpty = false, TimeAdded = false;
    private Calendar calendarGlobal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        overridePendingTransition(0, 0);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Set Reminder");

        //View_Setters
        scroll = findViewById(R.id.scroll);
        subjectEditTextLayout = findViewById(R.id.SubjectLayout);
        descriptionEditTextLayout = findViewById(R.id.DescriptionLayout);
        SubjectWayPoint = findViewById(R.id.SubjectWayPoint);
        TimeWayPoint = findViewById(R.id.TimeWayPoint);
        SetTime = findViewById(R.id.TimeTextView);
        SetDate = findViewById(R.id.DateTextView);

        //Methods Initialize
        Set_Time_Date("OnCreate");

        handler = new Handler();
    }

    @SuppressLint("SimpleDateFormat")
    private void Set_Time_Date(String When) {
        Calendar Calender_Time_Date = Calendar.getInstance();
        SimpleDateFormat Format;
        final boolean is24HourView = android.text.format.DateFormat.is24HourFormat(SetTime.getContext());
        if (is24HourView) {
            Format = new SimpleDateFormat("HH:mm");
        } else {
            Format = new SimpleDateFormat("hh:mm a");
        }
        switch (When) {
            case "OnCreate":
                SetTime.setText(Format.format(Calender_Time_Date.getTime()));
                if (Calender_Time_Date.get(Calendar.MONTH) > 9) {
                    Date = Calender_Time_Date.get(Calendar.DAY_OF_MONTH) + "-" + (Calender_Time_Date.get(Calendar.MONTH) + 1) + "-" + Calender_Time_Date.get(Calendar.YEAR);
                } else {
                    Date = Calender_Time_Date.get(Calendar.DAY_OF_MONTH) + "-0" + (Calender_Time_Date.get(Calendar.MONTH) + 1) + "-" + Calender_Time_Date.get(Calendar.YEAR);
                }
                SetDate.setText(Date);
                break;
            case "Date":
                Dialog_Date(Calender_Time_Date);
                break;
            case "Time":
                Dialog_Time(Calender_Time_Date);
                break;
        }
    }

    private void Dialog_Time(final Calendar Calender) {
        int Hour = Calender.get(Calendar.HOUR_OF_DAY);
        int Minutes = Calender.get(Calendar.MINUTE);
        final boolean is24HourView = android.text.format.DateFormat.is24HourFormat(SetTime.getContext());
        TimePickerDialog SetTimeDialog = new TimePickerDialog(this, R.style.TimePickerDialogAnother, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onTimeSet(TimePicker timePicker, int Hours, int Minutes) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE, Hours, Minutes);
                SimpleDateFormat timeFormat;
                if (is24HourView) {
                    timeFormat = new SimpleDateFormat("H:mm");
                } else {
                    timeFormat = new SimpleDateFormat("h:mm a");
                }

                calendarGlobal.set(calendarGlobal.get(Calendar.YEAR),
                        calendarGlobal.get(Calendar.MONTH),
                        calendarGlobal.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        calendar.get(Calendar.SECOND));

                Time = timeFormat.format(calendar.getTime());
                SetTime.setText(timeFormat.format(calendar.getTime()));
                TimeSet++;
            }
        }, Hour, Minutes, is24HourView);
        SetTimeDialog.show();
    }

    private void Dialog_Date(final Calendar Calender) {
        final int mYear = Calender.get(Calendar.YEAR);
        final int mMonth = Calender.get(Calendar.MONTH);
        int mDay = Calender.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog SetDateDialog = new DatePickerDialog(this, R.style.TimePickerDialogAnother,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (monthOfYear > 9) {
                            Date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        } else {
                            Date = dayOfMonth + "-" + "0" + (monthOfYear + 1) + "-" + year;
                        }
                        SetDate.setText(Date);
                        calendarGlobal.set(year, monthOfYear, dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        SetDateDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SetDateDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        SetDateDialog.show();
    }

    public void SetButtonOnClick(View v) {
        SetStringData();
    }

    private void SetStringData() {
        CheckBox tracker = findViewById(R.id.TrackerCheckBox);
        ReminderObject reminder;
        Subject = subjectEditTextLayout.getEditText().getText().toString();
        NotEmptyUpdate = TimeAddedUpdate = 0;
        TimeAdded = NotEmpty = ConditionSatisfied = false;

        handler.removeCallbacks(NotEmptyCondition);
        handler.removeCallbacks(TimeAddedCondition);

        NotEmptyCondition.run();
        TimeAddedCondition.run();

        if (ConditionSatisfied) {

            reminder = new ReminderObject(capitalize(subjectEditTextLayout.getEditText().getText().toString().trim()),
                    capitalize(descriptionEditTextLayout.getEditText().getText().toString().trim()), Date, Time);

            if (tracker.isChecked()) {

                Log.d("Time Passed: ", calendarGlobal.getTime().compareTo(Calendar.getInstance().getTime()) + "");

                if (isFuture(calendarGlobal, Calendar.getInstance())) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence name = "Forgetful Notification Channel";
                        String description = "Notification Channel for Reminders from Don't Forget";
                        int importance = NotificationManager.IMPORTANCE_HIGH;

                        NotificationChannel channel = new NotificationChannel("Reminders", name, importance);
                        channel.setDescription(description);

                        NotificationManager manager = getSystemService(NotificationManager.class);
                        assert manager != null;
                        manager.createNotificationChannel(channel);
                    }

                    Intent intent = new Intent(this, BroadCastNotification.class);
                    intent.putExtra("Title", reminder.Title);
                    intent.putExtra("Description", reminder.Description);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

                    AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

                    manager.set(AlarmManager.RTC_WAKEUP, calendarGlobal.getTimeInMillis(), pendingIntent);

                    new SchemaDataQueries().addReminder(reminder, this);
                    Intent pass = new Intent(this, Navigation.class);
                    pass.putExtra("id", getIntent().getIntExtra("id", R.id.reminder));
                    pass.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(pass);
                    Toast.makeText(this, "Remainder Set", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    KeyboardClose();
                    DoesNotMatch(getLayoutInflater().inflate(R.layout.dialog_reminder, null));
                }
            }
            else{
                new SchemaDataQueries().addReminder(reminder, this);
                Intent pass = new Intent(this, Navigation.class);
                pass.putExtra("id", getIntent().getIntExtra("id", R.id.reminder));
                pass.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(pass);
                Toast.makeText(this, "Remainder Set", Toast.LENGTH_SHORT).show();
                finish();
            }
            Log.d("Exit", String.valueOf(tracker.isChecked() && isFuture(calendarGlobal, Calendar.getInstance())));
        }
    }

    private final Runnable NotEmptyCondition = new Runnable() {
        @Override
        public void run() {
            if (subjectEditTextLayout.getEditText().getText().toString().isEmpty()) {
                NotEmpty = false;
                subjectEditTextLayout.setErrorEnabled(true);
                subjectEditTextLayout.setError("Subject Cannot be Empty");
                subjectEditTextLayout.getEditText().setFocusable(true);

                if (NotEmptyUpdate == 0) {
                    Scroll_To(((int) SubjectWayPoint.getY()));
                    KeyBoardOpen();
                }
            } else {
                NotEmptyUpdate = 0;
                NotEmpty = true;
                subjectEditTextLayout.setErrorEnabled(false);
                handler.removeCallbacks(NotEmptyCondition);
            }

            ConditionSatisfied = TimeAdded && NotEmpty;
            NotEmptyUpdate++;
            handler.postDelayed(this, 100);
        }
    };

    private final Runnable TimeAddedCondition = new Runnable() {
        @SuppressLint("ResourceAsColor")
        @Override
        public void run() {
            if (TimeSet == 0) {
                SetTime.setBackgroundResource(R.drawable.text_input_texture_error);
                SetTime.setTextColor(getResources().getColor(R.color.Theme_Color));
                if (TimeAddedUpdate == 0) {
                    Toast.makeText(getApplicationContext(), "Please Set Time", Toast.LENGTH_SHORT).show();
                    Scroll_To((int) TimeWayPoint.getY());
                }
            } else {
                TimeAdded = true;
                SetTime.setTextColor(getResources().getColor(R.color.BoxColor));
                SetTime.setBackgroundResource(R.drawable.text_input_texture);
                handler.removeCallbacks(TimeAddedCondition);
            }

            ConditionSatisfied = TimeAdded && NotEmpty;
            TimeAddedUpdate++;
            handler.postDelayed(this, 100);
        }
    };

    private void Scroll_To(int y) {
        scroll.scrollTo(0, y);
    }

    private void KeyBoardOpen() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(subjectEditTextLayout.getEditText(), InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void KeyboardClose() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private boolean isFuture(Calendar selectedTime, Calendar currentTime) {
        return (selectedTime.getTimeInMillis() > currentTime.getTimeInMillis());
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

    public void Date_Time_OnPress(View view) {
        if (view.getId() == (R.id.TimeTextView)) {
            Set_Time_Date("Time");
        } else if (view.getId() == (R.id.DateTextView)) {
            Set_Time_Date("Date");
        }
    }

    public void TrackingLayoutOnClick(View view) {
        CheckBox tracking = findViewById(R.id.TrackerCheckBox);
        if (!tracking.isChecked()) {
            tracking.setChecked(true);
        } else {
            tracking.setChecked(false);
        }
    }

    private String capitalize(String string) {
        switch (string.length()) {
            case 0:
                string = "None";
                break;
            case 1:
                string = string.toUpperCase();
                break;
            default:
                string = string.substring(0, 1).toUpperCase() + string.substring(1);
        }
        return string;
    }

    private void DoesNotMatch(View view) {
        TextView Dummy;
        final TextView Title;
        final TextView Description;
        int[] views = {R.id.DateDialog, R.id.TimeDialog, R.id.TimeTextViewDialog, R.id.DateTextViewDialog};
        View one, two;
        RelativeLayout relativeLayout = view.findViewById(R.id.RelativeDialogReminder);
        relativeLayout.setHorizontalGravity(RelativeLayout.CENTER_IN_PARENT);

        one = view.findViewById(R.id.ReminderDividerDialogOne);
        two = view.findViewById(R.id.ReminderDividerDialogTwo);
        one.setVisibility(View.GONE);
        two.setVisibility(View.GONE);

        Dummy = view.findViewById(R.id.SubjectDialog);
        Dummy.setText("Error:");

        Dummy = view.findViewById(R.id.DescriptionDialog);
        Dummy.setText("Solution:");

        Title = view.findViewById(R.id.TitleTextViewDialog);
        Title.setText("Time has passed");

        Description = view.findViewById(R.id.DescriptionTextViewDialog);
        Description.setText("Uncheck notify or set correct time");

        for (int i : views) {
            Dummy = view.findViewById(i);
            Dummy.setVisibility(View.GONE);
        }

        ImageButton button = view.findViewById(R.id.OkReminderDialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setView(view);
        final AlertDialog alert = builder.create();
        alert.getWindow().setBackgroundDrawableResource(R.drawable.dialog_transparent);
        alert.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
    }
}
