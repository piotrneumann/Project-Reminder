package com.example.piotr.project_reminder;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.util.ArrayList;

public class Task_Activity extends AppCompatActivity {

    ImageButton addFillTask;
    EditText myDescription;

    int id;
    String description;
    String date;
    int vibration;
    Task_DataBase db;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        final Intent intentReminder = new Intent(this, Reminder_Activity.class);

        myDescription = (EditText) findViewById(R.id.editText_description);
        addFillTask = (ImageButton) findViewById(R.id.button_addFillTask);
        db = new Task_DataBase(this);
        timePicker = (TimePicker) findViewById(R.id.timePicker);


        addFillTask.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                db.open();
                id = 1;
                description = myDescription.getText().toString();
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                date = Integer.toString(hour)+":"+Integer.toString(minute);
                vibration = 0;

                Task t = new Task(id, description, date, vibration);

                db.addTaskToDb(t);
                db.close();

                Log.d("TIME", Integer.toString(hour)+ " " + Integer.toString(minute));
                startActivity(intentReminder);
            }
        });

    }
}
