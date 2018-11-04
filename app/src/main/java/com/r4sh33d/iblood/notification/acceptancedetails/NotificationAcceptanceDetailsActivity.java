package com.r4sh33d.iblood.notification.acceptancedetails;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.models.AcceptanceNotificationData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.notification.requestdetails.RequestDetailsContract;
import com.r4sh33d.iblood.notification.requestdetails.RequestDetailsPresenter;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.PrefsUtils;
import com.r4sh33d.iblood.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.r4sh33d.iblood.notification.services.NotificationHandlerService.NOTIFICATION_OBJECT_ARGS;

public class NotificationAcceptanceDetailsActivity extends AppCompatActivity implements AcceptanceDetailsContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar_root)
    ConstraintLayout progressBarRoot;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.progress_message)
    TextView progressMessage;

    @BindView(R.id.header_info_textview)
    TextView headerInfoTextView;
    @BindView(R.id.full_name_textview)
    TextView fullNameTextView;
    @BindView(R.id.location_textview)
    TextView locationTextView;

    @BindView(R.id.religion_label)
    TextView religionLabel;
    @BindView(R.id.religion_textview)
    TextView religionTextView;

    @BindView(R.id.phone_number_textview)
    TextView phoneNumberTextView;

    @BindView(R.id.email_textview)
    TextView emailTextView;

    @BindView(R.id.finish_button)
    Button finishButton;

    PrefsUtils prefsUtils;
    UserData bloodSeekerData;

    AcceptanceDetailsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance_notification_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
         actionBar.setTitle("Blood request Accepted");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        prefsUtils = Provider.providePrefManager(this);
        presenter = new AcceptanceDetailsPresenter(this, Provider.provideDataRetrofitService(), prefsUtils);
        bloodSeekerData = prefsUtils.getPrefAsObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS, UserData.class);
        if (getIntent() != null) {
            handleNotificationIntent(getIntent());
        } else {
            // We miss road.
            finish();
        }
    }


    private void handleNotificationIntent(Intent intent) {
        if (intent.hasExtra(NOTIFICATION_OBJECT_ARGS)) {
            AcceptanceNotificationData notificationData = intent.getParcelableExtra(NOTIFICATION_OBJECT_ARGS);
            presenter.fetchDetails(notificationData);
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

    @OnClick(R.id.finish_button)
    public void onFinishButtonClicked() {
         finish();
    }

    @Override
    public void showLoading(String message) {
        ViewUtils.show(progressBarRoot, progressBar, progressMessage);
        progressMessage.setText(message);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void dismissLoading() {
        ViewUtils.hide(progressBarRoot);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void showError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    @Override
    public void onDetailsSuccessfullyFetched(UserData bloodDonorData) {
        headerInfoTextView.setText(String.format("Dear %s, %s %s  has accepted your blood donation request, " +
                "Their contact information is presented below alongside other relevant information", bloodSeekerData.firstName,
                bloodDonorData.firstName , bloodDonorData.lastName));
        fullNameTextView.setText(String.format("%s %s", bloodDonorData.firstName, bloodDonorData.lastName));
        locationTextView.setText(bloodDonorData.userLocation.descriptiveAddress); //TODO come back and check this
        religionTextView.setText(bloodDonorData.religion); //TODO come back and hide this based on religion option
        phoneNumberTextView.setText(bloodDonorData.phoneNumber);
        emailTextView.setText(bloodDonorData.email);
    }

}
