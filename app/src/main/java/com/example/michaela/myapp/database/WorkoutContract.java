package com.example.michaela.myapp.database;

import android.provider.BaseColumns;

public class WorkoutContract {

    private WorkoutContract(){}
    public static final class WorkoutEntry implements BaseColumns{
        public static final String TABLE_NAME = "WorkoutList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}