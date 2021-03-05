package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class WeeklyNotification extends AppCompatActivity implements View.OnClickListener {

    private int notificationId = 1;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_notification);

        // Set onClick Listener
        Button setTime =  findViewById(R.id.setBtn);
        Button cancel =  findViewById(R.id.cancelBtn);
        setTime.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        DatePicker datePicker = findViewById(R.id.timePicker);

        // Intent
        Intent intent = new Intent(WeeklyNotification.this, AlarmReceiver.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("message", "Your Weekly Expense Report");

        // PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                WeeklyNotification.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        // AlarmManager
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        switch (view.getId()) {
            case R.id.setBtn:
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();

                // Create date.
                Calendar date = Calendar.getInstance(Locale.FRENCH);
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DAY_OF_MONTH, day);
                long alarmdate = date.getTimeInMillis();

                // Set Alarm
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmdate,AlarmManager.INTERVAL_DAY, pendingIntent);
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