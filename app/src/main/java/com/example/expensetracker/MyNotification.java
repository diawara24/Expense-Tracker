package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MyNotification extends AppCompatActivity implements View.OnClickListener {

    private int notificationId = 1;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notification);

        // Set onClick Listener
        Button setTime =  findViewById(R.id.setBtn);
        Button cancel =  findViewById(R.id.cancelBtn);
        setTime.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        TimePicker timePicker = findViewById(R.id.timePicker);

        // Intent
        Intent intent = new Intent(MyNotification.this, AlarmReceiver.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("message", "Your Daily Expense Report");

        // PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                MyNotification.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        // AlarmManager
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        switch (view.getId()) {
            case R.id.setBtn:
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                // Create time.
                Calendar startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY, hour);
                startTime.set(Calendar.MINUTE, minute);
                startTime.set(Calendar.SECOND, 0);
                long alarmStartTime = startTime.getTimeInMillis();

                // Set Alarm
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime,AlarmManager.INTERVAL_DAY, pendingIntent);
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
                i = new Intent(this, MainActivity.class);
                setResult(Activity.RESULT_OK,i);
                finish();
                break;

            case R.id.cancelBtn:
                alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "Canceled.", Toast.LENGTH_SHORT).show();
                i = new Intent(this, MainActivity.class);
                setResult(Activity.RESULT_OK,i);
                finish();
                break;
        }

    }

}