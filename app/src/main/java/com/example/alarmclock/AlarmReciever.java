package com.example.alarmclock;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Button;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // creating the intent
        Intent i=new  Intent(context,DestinationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,i,0);

        // create the body of the notification
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"foxandroid")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Alarm notification")
                .setContentText("Its time to work ")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);// jumping  to the next activity


        // builds notifications
         NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
         notificationManagerCompat.notify(123,builder.build());

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if(uri==null) {
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context,uri);
        ringtone.play();
//        if(flag==1){
//            ringtone.stop();
//        }
    }
}

