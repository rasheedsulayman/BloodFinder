package com.r4sh33d.iblood.notification.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.r4sh33d.iblood.models.FCMNotification;
import com.r4sh33d.iblood.notification.NotificationDetailsActivity;

import java.util.Map;

/**
 * Created by rasheed on 12/19/17.
 */

public class NotificationHandlerService extends FirebaseMessagingService {
    public static final String NOTIFICATION_OBJECT_ARGS = "notification_object_extra_args";
    public static final String NOTIFICATION_TITLE_KEY = "title";
    public static final String NOTIFICATION_BODY_KEY = "body";
    private static final int NOTIFICATION_ID = 100;
    private static final String NOTIFICATION_CHANEL_ID = "my_channel_01";
    private String TAG = NotificationHandlerService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage != null && remoteMessage.getData() != null) {
            Map<String, String> data = remoteMessage.getData();
            if (data != null) {
                String title = data.get(NOTIFICATION_TITLE_KEY);
                String body = data.get(NOTIFICATION_BODY_KEY);
                long sentTime = remoteMessage.getSentTime();
                long timeArrived = System.currentTimeMillis();
                sendNotification(new FCMNotification(title, body, timeArrived, sentTime));
            }
        }
    }

    private void sendNotification(FCMNotification fcmNotification) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setUPNotificationChannel(notificationManager);
        }
        notificationManager.notify(NOTIFICATION_ID, getNotification(fcmNotification));
    }

    public Notification getNotification(FCMNotification fcmNotification) {
        Intent intent = new Intent(this, NotificationDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(NOTIFICATION_OBJECT_ARGS, fcmNotification);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        return new NotificationCompat.Builder(this, NOTIFICATION_CHANEL_ID)
                .setSmallIcon(R.mipmap.fbni_ic_launcher)
                .setContentTitle(fcmNotification.title)
                .setContentText(fcmNotification.body)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(fcmNotification.body))
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(soundUri)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setWhen(fcmNotification.timeArrived)
                .setContentIntent(pendingIntent).build();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void setUPNotificationChannel(NotificationManager notificationManager) {
        CharSequence name = getString(R.string.notification_channel_name);
        String description = getString(R.string.notification_channel_description);
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANEL_ID, name,
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.BLUE);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(channel);
    }
}
