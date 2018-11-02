package com.r4sh33d.iblood.notification;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.r4sh33d.iblood.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationAcceptanceDetailsActivity extends AppCompatActivity {

    @BindView(R.id.header_info_textview)
    TextView headerInfoTextView;
    @BindView(R.id.full_name_textview)
    TextView fullNameTextView;
    @BindView(R.id.location_textview)
    TextView locationTextView;

    @BindView(R.id.donation_type_textview)
    TextView donationTypeTextView;
    @BindView(R.id.religion_label)
    TextView religionLabel;
    @BindView(R.id.religion_textview)
    TextView religionTextView;

    @BindView(R.id.accept_button)
    Button acceptButton;

    @BindView(R.id.decline_button)
    Button declineButton;

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
            //We probably miss road
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


    @OnClick(R.id.accept_button)
    public void onAcceptButtonClicked () {

    }

    @OnClick(R.id.decline_button)
    public void onDeclineButtonClicked() {

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
