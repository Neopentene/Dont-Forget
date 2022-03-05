package com.example.dontforget;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dontforget.Reminders.ReminderObject;

public class BroadCastNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "Reminders")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(capitalize(intent.getStringExtra("Title")))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(capitalize(intent.getStringExtra("Description"))))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, notification.build());
    }

    private String capitalize(String string){
        switch(string.length()){
            case 0: string = "None";
                    break;
            case 1: string = string.toUpperCase();
                    break;
            default: string = string.substring(0, 1).toUpperCase() + string.substring(1);
        }
        return string;
    }
}
