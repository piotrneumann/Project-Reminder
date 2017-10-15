package com.example.piotr.project_reminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Piotr on 10.09.2017.
 */

public class MyDataBase extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_ID = "id_task";
    public static final String COLUMN_TIME = "time_task";
    public static final String COLUMN_DESCRIPTION = "description_task";
    public static final String COLUMN_VIBRATION = "vibration_task";

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + COLUMN_ID + " integer primary key, "
            + COLUMN_TIME + " text not null, "
            + COLUMN_DESCRIPTION + " text, "
            + COLUMN_VIBRATION + " integer);";

    public MyDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
