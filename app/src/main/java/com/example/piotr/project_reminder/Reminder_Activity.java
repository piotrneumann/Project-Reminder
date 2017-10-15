package com.example.piotr.project_reminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Reminder_Activity extends Activity {

    ImageButton addTask;
    Button alarmOff;
    Task_DataBase db;
    ListView listView;
    ArrayList<Task> tasks;
    ArrayAdapter<Task> adapter;
    private PendingIntent pendingIntent;
    AlarmManager alarmManager;

    final Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        db = new Task_DataBase(this);
        final Intent intentTask = new Intent(this, Task_Activity.class);
        addTask = (ImageButton) findViewById(R.id.button_addTask);
        listView = (ListView) findViewById(R.id.listView);
        alarmOff = (Button) findViewById(R.id.alarm_off);
        db.open();
        final Handler handler = new Handler();

        Intent alarmReceiver = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(activity.getApplicationContext(),0,alarmReceiver,0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        updateList();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),0, pendingIntent);
        alarmOff();


        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Toast.makeText(getApplicationContext(), "Alarm OFF", Toast.LENGTH_LONG).show();
//
//                //alarmManager.cancel(p1);
//            }
//        },10000);



        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("pos", Integer.toString(position));
                Toast.makeText(getApplicationContext(),Integer.toString(position),Toast.LENGTH_SHORT).show();

                SparseBooleanArray checkedTasks = listView.getCheckedItemPositions();
                Log.d("tl",Integer.toString(checkedTasks.size()));
                for(int i=0;i<checkedTasks.size();i++) {
                    if(checkedTasks.valueAt(i)){
                        Log.d("Sprawdzenie",Integer.toString(i));
                        alarmOn(i);
                    }
                }
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.open();
                ArrayList<Task> tasks = db.getAll();

                for (Task ta : tasks) {
                    String log = ta.toString();
                    Log.d("Name", log);
                }
                db.close();
                startActivity(intentTask);
            }
        });

        alarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmOff();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add(Menu.NONE,0,0,"Usun");
            menu.add(Menu.NONE,1,1,"Edytuj");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        Log.d("POzycja", Integer.toString(info.position));
        final Intent intentTask = new Intent(this, Task_Activity.class);
        switch(item.getItemId()) {
            case 0: db.deteteTaskFromDb(tasks.get(info.position));
                updateList();
                break;
            case 1: startActivity(intentTask); break;
        }
        Log.d("tak", Integer.toString(menuItemIndex));
        updateList();
        return true;
    }

    void alarmOn(int i) {
        Log.d("Alarm", "Alarm ON");
        //Toast.makeText(getApplicationContext(),tasks.get(i).toString(),Toast.LENGTH_SHORT).show();

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),0, pendingIntent);
    }

    void alarmOff() {
        Log.d("Alarm", "Alarm OFF");
        alarmManager.cancel(pendingIntent);
    }

    void updateList() {
        tasks = db.getAll();
        adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_multiple_choice,tasks);
        listView.setAdapter(adapter);
    }
}
