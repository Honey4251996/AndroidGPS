package com.example.michaela.myapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.michaela.myapp.database.WorkoutContract.*;

public class WorkoutDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Workoutlist.db";
    public static final int DATABASE_VERSION = 1;


    public WorkoutDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WORKOUTLIST_TABLE = "CREATE TABLE " +
                WorkoutEntry.TABLE_NAME + " (" +
                WorkoutEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WorkoutEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                WorkoutEntry.COLUMN_AMOUNT + " INTEGER NOT NULL, " +
                WorkoutEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_WORKOUTLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WorkoutEntry.TABLE_NAME);
        onCreate(db);
    }
}
