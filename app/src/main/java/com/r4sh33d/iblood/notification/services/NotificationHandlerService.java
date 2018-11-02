package com.r4sh33d.iblood.notification.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.models.AcceptanceNotificationData;
import com.r4sh33d.iblood.models.BaseNotificationData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.FCMNotification;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.notification.NotificationAcceptanceDetailsActivity;
import com.r4sh33d.iblood.notification.NotificationRequestDetailsActivity;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.PrefsUtils;

import java.util.Map;

import timber.log.Timber;

/**
 * Created by rasheed on 12/19/17.
 */

public class NotificationHandlerService extends FirebaseMessagingService {

    public static final String BLOOD_REQUEST_NOTIFICATION_TYPE = "blood_request_type";
    public static final String ACCEPTANCE_NOTIFICATION_TYPE = "acceptance_type";
    public static final String NOTIFICATION_OBJECT_ARGS = "notification_object_extra_args";
    public static final String NOTIFICATION_TITLE_KEY = "title";
    public static final String NOTIFICATION_BODY_KEY = "body";
    private static final int NOTIFICATION_ID = 100;
    private static final String NOTIFICATION_CHANEL_ID = "my_channel_01";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage != null && remoteMessage.getData() != null) {
            Map<String, String> data = remoteMessage.getData();
            if (data != null) {
                Timber.d("Notification received: " + data);
                String title = data.get(NOTIFICATION_TITLE_KEY);
                String body = data.get(NOTIFICATION_BODY_KEY);
                long sentTime = remoteMessage.getSentTime();
                long timeArrived = System.currentTimeMillis();
                switch (data.get("type")){
                    case BLOOD_REQUEST_NOTIFICATION_TYPE:
                        BloodRequestNotificationData notificationData = new BloodRequestNotificationData(data);
                        notificationData.sentTime = sentTime;
                        notificationData.timeArrived = timeArrived;
                        sendBloodRequestNotification(notificationData);
                        break;
                    case ACCEPTANCE_NOTIFICATION_TYPE:
                        AcceptanceNotificationData notificationData1 = new AcceptanceNotificationData(data);
                        notificationData1.sentTime = sentTime;
                        notificationData1.timeArrived = timeArrived;
                        sendAcceptanceNotification(notificationData1);
                        break;
                }
            }
        }
    }

    private void sendBloodRequestNotification(BloodRequestNotificationData notificationData) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setUPNotificationChannel(notificationManager);
        }
        notificationManager.notify(NOTIFICATION_ID, getBloodRequestNotification(notificationData));
    }

    private void sendAcceptanceNotification(AcceptanceNotificationData notificationData) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setUPNotificationChannel(notificationManager);
        }
        notificationManager.notify(NOTIFICATION_ID, getAcceptanceMessageNotification(notificationData));
    }


    public Notification getBloodRequestNotification(BloodRequestNotificationData bloodRequestNotificationData) {
        String donorsName = getUserName();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String notificationBody = String.format(" Hello %s, %s would like to receive blood donation from you",
                donorsName, bloodRequestNotificationData.bloodSeekerName);
        PendingIntent pendingIntent = getPendingIntent(NotificationRequestDetailsActivity.class, bloodRequestNotificationData);
        return new NotificationCompat.Builder(this, NOTIFICATION_CHANEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Blood donation request")
                .setContentText(notificationBody)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notificationBody))
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(soundUri)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setWhen(bloodRequestNotificationData.timeArrived)
                .setContentIntent(pendingIntent).build();
    }

    public PendingIntent getPendingIntent(Class clazz, Parcelable intentExtra) {
        Intent intent = new Intent(this, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(NOTIFICATION_OBJECT_ARGS, intentExtra);
        return PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
    }

    public String getUserName() {
        PrefsUtils prefsUtils = Provider.providePrefManager(this);
        if (prefsUtils.doesContain(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS)) {
            UserData userData = Provider.providePrefManager(this).
                    getPrefAsObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS, UserData.class);
            return userData.name;
        }
        return "";
    }


    public Notification getAcceptanceMessageNotification(AcceptanceNotificationData acceptanceNotificationData) {
        String seekersName = getUserName();
        PendingIntent pendingIntent = getPendingIntent(NotificationAcceptanceDetailsActivity.class, acceptanceNotificationData);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String notificationBody = String.format(" Hello %s, %s has accepted blood donation request. " +
                        "You can click this notification to view their contact information",
                seekersName, acceptanceNotificationData.bloodDonorName);
        return new NotificationCompat.Builder(this, NOTIFICATION_CHANEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Blood donation request accepted!")
                .setContentText(notificationBody)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notificationBody))
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(soundUri)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setWhen(acceptanceNotificationData.timeArrived)
                .setContentIntent(pendingIntent).build();
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void setUPNotificationChannel(NotificationManager notificationManager) {
        CharSequence name = getString(R.string.iblood_channel_name);
        String description = getString(R.string.iblood_channel_description);
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
