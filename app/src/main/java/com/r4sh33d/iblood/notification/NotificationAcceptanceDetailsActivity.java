package com.r4sh33d.iblood.notification;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.r4sh33d.iblood.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAcceptanceDetailsActivity extends AppCompatActivity {

    @BindView(R.id.notification_title)
    TextView notificationTitle;
    @BindView(R.id.time_sent)
    TextView notificationTime;
    @BindView(R.id.notification_details)
    TextView notificationDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle(R.string.view_notification);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        if (getIntent()!=null){
            // HandleNotificationIntent(getIntent());
        }else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

   /* private void populateViews(FCMNotification fcmNotification) {
        notificationTitle.setText(fcmNotification.title);
        notificationDetails.setText(fcmNotification.body);
        notificationTime.setText(DateUtils.getRelativeSentFromMessageWithTime(fcmNotification.sentTime));
    }

    private void HandleNotificationIntent(Intent intent) {
        if (intent.hasExtra(NOTIFICATION_OBJECT_ARGS)){
            FCMNotification fcmNotification = intent.getParcelableExtra(NOTIFICATION_OBJECT_ARGS);
                 populateViews(fcmNotification);
        }
    }*/
}
