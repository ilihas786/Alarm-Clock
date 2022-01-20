package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alarmclock.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding binding;
MaterialTimePicker picker;
private Calendar calendar;
private AlarmManager alarmManager;
 private  PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createnotificationChannel();
        binding=ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.selecttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePicker();


            }
        });

        binding.setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SetAlarm();
            }
        });
        binding.CancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();

            }
        });
    }

    private void cancelAlarm() {

        Intent intent=new Intent(this,AlarmReciever.class);

        // get the pending intent of the alarmReceiever CLass to MainActivity class
        pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);

        if(alarmManager==null){
            alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }


        alarmManager.cancel(pendingIntent);
        AlarmReciever alarmReciever;
        Toast.makeText(this, "ALARM CANCELLED SUCESSFULLY", Toast.LENGTH_SHORT).show();

    }

    private void SetAlarm() {
        alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,AlarmReciever.class);

        // get the pending intent of the alarmReceiever CLass to MainActivity class
        pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);

        // this will set alarm exact after the 1 minute of the time set
       alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        Toast.makeText(this, "ALARM SET SUCESSFULLY", Toast.LENGTH_SHORT).show();

        


    }

    private void showTimePicker() {
// creating the picker to pick the time
picker=new MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setHour(12)
        .setMinute(0)
        .setTitleText("Select  The Alarm ")
        .build();
 picker.show(getSupportFragmentManager(),"foxandroid");

 // if the time is rotated the clockwise
picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if(picker.getHour()>12){
            // adjusting the textr view according to the time selected by the user
            binding.timeshow.setText(
                    String.format("%02d",(picker.getHour()-12))+" : "+String.format("%02d",picker.getMinute())+" PM"
            );
        }
        else{
   binding.timeshow.setText(picker.getHour()+" : " + picker.getMinute() + " AM");
        }


        // set the time in picker to the calender
 calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
        calendar.set(Calendar.MINUTE,picker.getMinute());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

    }
});



    }

    // creation of the notification channel
    private void createnotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="foxandroindReminderChannel"; // name of the channel
            String description="channel for thr alarm manager"; // about the channel
            int importance= NotificationManager.IMPORTANCE_HIGH; // importance of the channel
            NotificationChannel notificationChannel=new NotificationChannel("foxandroid",name,importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}