package com.example.dontforget;

import static android.provider.BaseColumns._ID;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TableSchema extends SQLiteOpenHelper {
    public static class NoteSchema {
        public String TABLE_NAME = "Notes";
        public String ID_COLUMN = _ID;
        public String TITLE_COLUMN = "Title";
        public String DESCRIPTION_COLUMN = "Description";
        public String columns[] = {ID_COLUMN, TITLE_COLUMN, DESCRIPTION_COLUMN};
    }

    public static class RemainderSchema {
        public String TABLE_NAME = "Reminder";
        public String ID_COLUMN = _ID;
        public String TITLE_COLUMN = "Title";
        public String DESCRIPTION_COLUMN = "Description";
        public String DATE_COLUMN = "Date";
        public String TIME_COLUMN = "Time";
        public String columns[] = {ID_COLUMN, TITLE_COLUMN, DESCRIPTION_COLUMN, DATE_COLUMN, TIME_COLUMN};
    }

    public static String DATABASE_NAME = "Forgetful";

    public TableSchema(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        RemainderSchema remainderSchema = new RemainderSchema();
        NoteSchema noteSchema = new NoteSchema();
        String createRemaindersTable = "Create Table if not exists " + remainderSchema.TABLE_NAME +
                "(" +
                remainderSchema.ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                remainderSchema.TITLE_COLUMN + " TEXT, " +
                remainderSchema.DESCRIPTION_COLUMN + " TEXT," +
                remainderSchema.DATE_COLUMN + " TEXT," +
                remainderSchema.TIME_COLUMN + " TEXT" +
                ")";
        String createNotesTable = "Create Table if not exists " + noteSchema.TABLE_NAME +
                "(" +
                noteSchema.ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                noteSchema.TITLE_COLUMN + " TEXT, " +
                noteSchema.DESCRIPTION_COLUMN + " TEXT" +
                ")";
        db.execSQL(createRemaindersTable);
        db.execSQL(createNotesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
