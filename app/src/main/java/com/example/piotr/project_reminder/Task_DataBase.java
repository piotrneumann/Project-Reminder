package com.example.piotr.project_reminder;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 10.09.2017.
 */

public class Task_DataBase {

    private SQLiteDatabase database;
    private MyDataBase dbHelper;
    private String[] allColumns = {MyDataBase.COLUMN_ID, MyDataBase.COLUMN_TIME, MyDataBase.COLUMN_DESCRIPTION, MyDataBase.COLUMN_VIBRATION};

    public Task_DataBase(Context context) {
        dbHelper = new MyDataBase(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addTaskToDb(Task task) {
        ContentValues values = new ContentValues();
        values.put(MyDataBase.COLUMN_TIME, task.getTime());
        values.put(MyDataBase.COLUMN_DESCRIPTION, task.getDescripion());
        values.put(MyDataBase.COLUMN_VIBRATION, task.getVibration());
        Log.d("Task", task.toString());
        long id = database.insert(MyDataBase.TABLE_NAME, null, values);
    }

    public void deteteTaskFromDb(Task task) {
        String id = Integer.toString(task.getId());
        database.delete(MyDataBase.TABLE_NAME,MyDataBase.COLUMN_ID + " = " + id,null);
    }
    public ArrayList<Task> getAll() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        Cursor cursor = database.rawQuery("select * from tasks", null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            int id = Integer.parseInt(cursor.getString(0));
            String desc = cursor.getString(2);
            String time = cursor.getString(1);
            int vibr = Integer.parseInt(cursor.getString(3));
            Task t = new Task(id, time, desc, vibr);
            tasks.add(t);
            cursor.moveToNext();
        }
        return tasks;
    }

}
