package com.example.dontforget.Reminders;

public class ReminderObject {
    public String Title, Description, Date, Time;
    public long id;

    public ReminderObject(String Title, String Description, String Date, String Time) {
        this.Title = Title;
        this.Description = Description;
        this.Date = Date;
        this.Time = Time;
    }

    public ReminderObject(long id, String Title, String Description, String Date, String Time) {
        this.id = id;
        this.Title = Title;
        this.Description = Description;
        this.Date = Date;
        this.Time = Time;
    }
}
