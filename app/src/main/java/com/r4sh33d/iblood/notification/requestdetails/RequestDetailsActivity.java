package com.r4sh33d.iblood.notification.requestdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.models.AcceptanceNotificationData;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.NotificationPayload;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.r4sh33d.iblood.notification.services.NotificationHandlerService.ACCEPTANCE_NOTIFICATION_TYPE;
import static com.r4sh33d.iblood.notification.services.NotificationHandlerService.BLOOD_REQUEST_NOTIFICATION_TYPE;
import static com.r4sh33d.iblood.notification.services.NotificationHandlerService.NOTIFICATION_OBJECT_ARGS;


public class RequestDetailsActivity extends AppCompatActivity implements RequestDetailsContract.View {

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
    private UserData bloodSeekerData;
    private BloodPostingData bloodPostingData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_notification_details);
        ButterKnife.bind(this);
        acceptButton.setEnabled(false);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Blood Request");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        presenter = new RequestDetailsPresenter(this,
                Provider.provideDataRetrofitService(), Provider.providePrefManager(this),
                Provider.provideNotificationRetrofitService());
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
    public void onDetailsSuccessfullyFetched(UserData bloodSeekerData, BloodPostingData bloodPostingData) {
        this.bloodSeekerData = bloodSeekerData;
        this.bloodPostingData = bloodPostingData;
        acceptButton.setEnabled(true);
        headerInfoTextView.setText(String.format("Dear %s, %s %s would like to receive blood donation from you. If you are okay with the request, " +
                "Please acknowledge it by clicking the acceptance button below. After you accept the request," +
                " Your contact information will be shared with them. Their detailed information is presented below", bloodPostingData.donorsName, bloodSeekerData.firstName, bloodSeekerData.lastName));
        fullNameTextView.setText(String.format("%s %s", bloodSeekerData.firstName, bloodSeekerData.lastName));
        locationTextView.setText(bloodSeekerData.userLocation.descriptiveAddress); //TODO come back and check this
        donationTypeTextView.setText(bloodPostingData.donationType);
        religionTextView.setText(bloodSeekerData.religion); //TODO come back and hide this based on religion option
    }

    @Override
    public void onNotificationSuccessfullySent() {
        new MaterialDialog.Builder(this)
                .title("Acceptance message sent")
                .positiveColor(getResources().getColor(R.color.blood_red))
                .content("Your contact details has been shared with " + bloodSeekerData.firstName + " "
                        + bloodSeekerData.lastName + ". They will contact you soon.")
                .positiveText("Okay")
                .cancelable(false)
                .onPositive((dialog, which) -> {
                    //We are done here
                    finish();
                }).show();
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
        new MaterialDialog.Builder(this)
                .title("Send Confirmation ?")
                .positiveColor(getResources().getColor(R.color.blood_red))
                .negativeColor(getResources().getColor(R.color.blood_red))
                .content(String.format("A Confirmation message will be sent to %s %s", bloodSeekerData.firstName, bloodSeekerData.lastName))
                .positiveText("Send")
                .negativeText("Cancel")
                .onPositive((dialog, which) -> {
                    AcceptanceNotificationData notificationData =
                            new AcceptanceNotificationData(
                                    ACCEPTANCE_NOTIFICATION_TYPE,
                                    bloodPostingData.donorsFirebaseId,
                                    bloodPostingData.donorsName,
                                    bloodPostingData.id);
                    NotificationPayload<AcceptanceNotificationData> notificationPayload
                            = new NotificationPayload<>(notificationData, bloodSeekerData.notificationToken);
                    presenter.sendNotification(bloodSeekerData, notificationPayload);
                })
                .onNegative((dialog, which) -> {
                    //noOp
                })
                .show();
    }

    @OnClick(R.id.decline_button)
    public void onDeclineButtonClicked() {
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
}
