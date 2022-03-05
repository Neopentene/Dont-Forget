package com.example.dontforget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dontforget.Reminders.*;
import com.example.dontforget.Notes.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SchemaDataQueries {

    private TableSchema.RemainderSchema schemaReminder;
    private TableSchema.NoteSchema schemaNotes;
    private TableSchema tableSchema;

    private SQLiteDatabase db;

    public void addReminder(ReminderObject reminder, Context context) {
        tableSchema = new TableSchema(context);
        schemaReminder = new TableSchema.RemainderSchema();
        db = tableSchema.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(schemaReminder.TITLE_COLUMN, reminder.Title);
        content.put(schemaReminder.DESCRIPTION_COLUMN, reminder.Description);
        content.put(schemaReminder.DATE_COLUMN, reminder.Date);
        content.put(schemaReminder.TIME_COLUMN, reminder.Time);
        long addNote = db.insert(schemaReminder.TABLE_NAME, null, content);
    }

    public void addNotes(Note note, Context context) {
        tableSchema = new TableSchema(context);
        schemaNotes = new TableSchema.NoteSchema();
        db = tableSchema.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(schemaNotes.TITLE_COLUMN, note.Title);
        content.put(schemaNotes.DESCRIPTION_COLUMN, note.Description);
        long addNote = db.insert(schemaNotes.TABLE_NAME, null, content);
    }

    public ArrayList<Note> getAllNotes(Context context) {
        tableSchema = new TableSchema(context);
        ArrayList<Note> notes = new ArrayList<Note>();
        schemaNotes = new TableSchema.NoteSchema();
        db = tableSchema.getReadableDatabase();
        String columns[] = schemaNotes.columns;
        String orderBy = schemaNotes.ID_COLUMN + " ASC";
        Cursor cursor = db.query(schemaNotes.TABLE_NAME, columns, null, null,
                null, null, orderBy);
        while (cursor.moveToNext()) {
            notes.add(new Note(Long.parseLong(cursor.getString(cursor.getColumnIndex(columns[0]))),
                    cursor.getString(cursor.getColumnIndex(columns[1])),
                    cursor.getString(cursor.getColumnIndex(columns[2]))));
        }
        return notes;
    }

    public ArrayList<ReminderObject> getAllRemainder(Context context) {
        tableSchema = new TableSchema(context);
        ArrayList<ReminderObject> notes = new ArrayList<ReminderObject>();
        schemaReminder = new TableSchema.RemainderSchema();
        db = tableSchema.getReadableDatabase();
        String columns[] = schemaReminder.columns;
        String orderBy = schemaReminder.ID_COLUMN + " DESC";
        Cursor cursor = db.query(schemaReminder.TABLE_NAME, columns, null, null,
                null, null, orderBy);
        while (cursor.moveToNext()) {
            notes.add(new ReminderObject(Long.parseLong(cursor.getString(cursor.getColumnIndex(columns[0]))),
                    cursor.getString(cursor.getColumnIndex(columns[1])),
                    cursor.getString(cursor.getColumnIndex(columns[2])),
                    cursor.getString(cursor.getColumnIndex(columns[3])),
                    cursor.getString(cursor.getColumnIndex(columns[4]))));
        }
        return notes;
    }

    public long updateNote(Context context, long id, String newTitle, String newDescription) {
        tableSchema = new TableSchema(context);
        schemaNotes = new TableSchema.NoteSchema();
        db = tableSchema.getWritableDatabase();
        String Clause = schemaNotes.ID_COLUMN + " = ?";
        String args[] = {String.valueOf(id)};
        ContentValues content = new ContentValues();
        content.put(schemaNotes.TITLE_COLUMN, newTitle);
        content.put(schemaNotes.DESCRIPTION_COLUMN, newDescription);
        long update = db.update(schemaNotes.TABLE_NAME, content, Clause, args);
        return update;
    }

    public void deleteNote(Context context, long id) {
        tableSchema = new TableSchema(context);
        schemaNotes = new TableSchema.NoteSchema();
        db = tableSchema.getWritableDatabase();
        String Clause = schemaNotes.ID_COLUMN + " = ?";
        String args[] = {String.valueOf(id)};
        long delete = db.delete(schemaNotes.TABLE_NAME, Clause, args);
    }

    public void deleteReminder(Context context, long id) {
        tableSchema = new TableSchema(context);
        schemaReminder = new TableSchema.RemainderSchema();
        db = tableSchema.getWritableDatabase();
        String Clause = schemaReminder.ID_COLUMN + " = ?";
        String args[] = {String.valueOf(id)};
        long delete = db.delete(schemaReminder.TABLE_NAME, Clause, args);
    }
}
