package com.example.dontforget.Notes;

public class Note {
    public String Title, Description;

    public long id;

    public Note(String title, String description) {
        this.Title = title;
        this.Description = description;
    }

    public Note(long id, String title, String description) {
        this.id = id;
        this.Title = title;
        this.Description = description;
    }

}
