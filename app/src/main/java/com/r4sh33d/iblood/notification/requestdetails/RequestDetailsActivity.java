package com.r4sh33d.iblood.notification.requestdetails;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.models.AcceptanceNotificationData;
import com.r4sh33d.iblood.models.BloodDonationCenter;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.NotificationPayload;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.utils.CustomSpinnerAdapter;
import com.r4sh33d.iblood.utils.Data;
import com.r4sh33d.iblood.utils.Utils;
import com.r4sh33d.iblood.utils.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.r4sh33d.iblood.notification.services.NotificationHandlerService.ACCEPTANCE_NOTIFICATION_TYPE;
import static com.r4sh33d.iblood.notification.services.NotificationHandlerService.NOTIFICATION_OBJECT_ARGS;


public class RequestDetailsActivity extends AppCompatActivity implements RequestDetailsContract.View,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

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

    @BindView(R.id.date_value)
    TextView dateValueTextView;

    @BindView(R.id.time_value)
    TextView timeValueTextView;

    @BindView(R.id.blood_bank_spinner)
    Spinner bloodBankSpinner;

    String dateString = "";
    String timeString = "";

    RequestDetailsContract.Presenter presenter;
    private UserData bloodSeekerData;
    private BloodPostingData bloodPostingData;
    private Calendar startingDateCalender;


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
        prepareBloodDonationCentersSpinner();
        startingDateCalender = Calendar.getInstance();
        Utils.setCalenderDefault(startingDateCalender);

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
        headerInfoTextView.setText(String.format("Dear %s, %s %s would like to receive blood donation from you. If you are okay with the request," +
                        " Please acknowledge it by scheduling an appointment with them at one of the presented blood donation centers." +
                        " After you accept the request, A message containing details of the schedule will be sent to them.",
                bloodPostingData.donorsName, bloodSeekerData.firstName, bloodSeekerData.lastName));
        fullNameTextView.setText(String.format("%s %s", bloodSeekerData.firstName, bloodSeekerData.lastName));
        //  locationTextView.setText(bloodSeekerData.miniLocation.descriptiveAddress); //TODO come back and check this
        donationTypeTextView.setText(bloodPostingData.donationType);
        religionTextView.setText(bloodSeekerData.religion); //TODO come back and hide this based on religion option
    }

    @Override
    public void onNotificationSuccessfullySent() {
        new MaterialDialog.Builder(this)
                .title("Acceptance message sent")
                .positiveColor(getResources().getColor(R.color.blood_red))
                .content("Your donation schedule info has been shared with " + bloodSeekerData.firstName + " "
                        + bloodSeekerData.lastName + ". Please try to keep the appointment")
                .positiveText("Okay")
                .cancelable(false)
                .onPositive((dialog, which) -> {
                    //We are done here
                    finish();
                }).show();
    }

    void prepareBloodDonationCentersSpinner() {
        CustomSpinnerAdapter<BloodDonationCenter> listOfBloodCenterAdapter = new CustomSpinnerAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, Data.bloodDonationCenters);
        bloodBankSpinner.setAdapter(listOfBloodCenterAdapter);
    }


    @Override
    public void onNearbyBloodBanksFetched(ArrayList<BloodDonationCenter> donationCenters) {

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
        if (bloodBankSpinner.getSelectedItemPosition() < 1) {
            showToast("Please select a blood donation center to proceed");
            return;
        }
        if (TextUtils.isEmpty(dateString)) {
            showToast("Please select a valid date to proceed");
            return;
        }
        if (TextUtils.isEmpty(timeString)) {
            showToast("Please select a valid time to proceed");
            return;
        }
        showSendConfirmationDialog();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    void showSendConfirmationDialog() {
        BloodDonationCenter selectedDonationCenter = (BloodDonationCenter) bloodBankSpinner.getSelectedItem();
        new MaterialDialog.Builder(this)
                .title("Send Confirmation ?")
                .positiveColor(getResources().getColor(R.color.blood_red))
                .negativeColor(getResources().getColor(R.color.blood_red))
                .content(String.format("A Confirmation message and the schedule info will be sent to %s %s",
                        bloodSeekerData.firstName, bloodSeekerData.lastName))
                .positiveText("Send")
                .negativeText("Cancel")
                .onPositive((dialog, which) -> {
                    AcceptanceNotificationData notificationData =
                            new AcceptanceNotificationData(
                                    ACCEPTANCE_NOTIFICATION_TYPE,
                                    bloodPostingData.donorsFirebaseId,
                                    bloodPostingData.donorsName,
                                    bloodPostingData.id, selectedDonationCenter.name,
                                    String.valueOf(selectedDonationCenter.miniLocation.latitude),
                                    String.valueOf(selectedDonationCenter.miniLocation.longitude),
                                    selectedDonationCenter.googleMapName,
                                    String.valueOf(startingDateCalender.getTimeInMillis()));
                    NotificationPayload<AcceptanceNotificationData> notificationPayload
                            = new NotificationPayload<>(notificationData, bloodSeekerData.notificationToken);
                    presenter.sendNotification(bloodSeekerData, notificationPayload);
                })
                .onNegative((dialog, which) -> {

                    //noOp
                })
                .show();
    }


    @OnClick(R.id.view_selected_donation_center_button)
    void onClickViewSelectedDonationCenter() {
        if (bloodBankSpinner.getSelectedItemPosition() < 1) {
            showToast("Please select a blood donation center to proceed");
            return;
        }
        BloodDonationCenter selectedDonationCenter = (BloodDonationCenter) bloodBankSpinner.getSelectedItem();
        startActivity(Utils.getMapsIntent(selectedDonationCenter.googleMapName));
    }

    @OnClick(R.id.date_linearlayout)
    void onClickStartingDate() {
        showDatePicker();
    }


    @OnClick(R.id.time_linearlayout)
    void onClickStartingTimeValue() {
        showTimePickerDialog();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
        startingDateCalender.set(year, month, dayOfMonth);
        dateString = month_date.format(startingDateCalender.getTime());
        dateValueTextView.setText(dateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        startingDateCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
        startingDateCalender.set(Calendar.MINUTE, minute);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        timeString = dateFormat.format(startingDateCalender.getTime());
        timeValueTextView.setText(timeString);
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

    void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    void showTimePickerDialog() {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        // Create a new instance of TimePickerDialog and return it
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, hour, minute,
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
}
