package com.example.rotibanksystem;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase("CONFIRM")) {

            Toast.makeText(context, "Booking your ride", Toast.LENGTH_SHORT).show();


        } else if (intent.getAction().equalsIgnoreCase("CANCEL")) {

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(11111);

        }
    }
}
