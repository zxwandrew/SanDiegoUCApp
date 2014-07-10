package com.esri.UC;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.esri.android.geotrigger.GeotriggerBroadcastReceiver;

import java.util.Set;

/**
 * Created by Andrew on 7/3/14.
 */
public class GCMHandler extends GeotriggerBroadcastReceiver {
    @Override
    protected void onPushMessage(Context context, Bundle notification) {
        //super.onPushMessage(context, data);

        // The notification Bundle has these keys: 'text', 'url', 'sound', 'icon', 'data'
        //notification
        Object[] keySet = notification.keySet().toArray();
        for (String s : notification.keySet()) {
            Log.e("allkeys", s);
        }

        if(notification.containsKey("data")) {
            Log.e("data", notification.get("data").toString());
            String msg = String.format("Push Message Received: %s", notification.get("data"));


            //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("Geotrigger Notification")
                            .setContentText(notification.get("text").toString())
                            .setAutoCancel(true);

            Intent resultIntent = new Intent(context, MainActivity.class);
            resultIntent.putExtra("NewNotification", true);
            resultIntent.putExtras(notification);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);


            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);

            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(1, mBuilder.build());

        }

    }
}