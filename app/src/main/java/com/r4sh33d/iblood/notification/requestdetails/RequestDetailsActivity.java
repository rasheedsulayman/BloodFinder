package com.r4sh33d.iblood.notification.requestdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.base.BaseActivity;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.r4sh33d.iblood.notification.services.NotificationHandlerService.NOTIFICATION_OBJECT_ARGS;


public class RequestDetailsActivity extends AppCompatActivity implements RequestDetailsContract.View {

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

    RequestDetailsContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle(R.string.view_notification);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        presenter = new RequestDetailsPresenter(this,
                Provider.provideDataRetrofitService(), Provider.providePrefManager(this));
        if (getIntent() != null) {
            handleNotificationIntent(getIntent());
        } else {
            finish();
        }
    }

    private void handleNotificationIntent(Intent intent) {
        if (intent.hasExtra(NOTIFICATION_OBJECT_ARGS)) {
            BloodRequestNotificationData bloodRequestNotificationData = intent.getParcelableExtra(NOTIFICATION_OBJECT_ARGS);
            presenter.fetchDetails(bloodRequestNotificationData);
        }
    }


    @Override
    public void onDetailsSuccessfullyFetched(UserData user, BloodPostingData bloodPostingData) {
        fullNameTextView.setText(user.name);
        locationTextView.setText(user.address);//TODO come back and check this
        donationTypeTextView.setText(bloodPostingData.donationType);
        religionTextView.setText(user.religion); //TODO come back and hide this based on religion option


    }

    @Override
    public void onBloodPostingSuccessfullyFetched() {

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
    public void onAcceptButtonClicked() {

    }

    @OnClick(R.id.decline_button)
    public void onDeclineButtonClicked() {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showAlertDialog(String message) {

    }

}
