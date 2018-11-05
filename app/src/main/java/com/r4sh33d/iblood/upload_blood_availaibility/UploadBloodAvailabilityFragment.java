package com.r4sh33d.iblood.upload_blood_availaibility;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.models.KeyNameLOVPair;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.Constants.BloodPostingStatus;
import com.r4sh33d.iblood.utils.CustomSpinnerAdapter;
import com.r4sh33d.iblood.utils.PrefsUtils;
import com.r4sh33d.iblood.utils.Utils;
import com.r4sh33d.iblood.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadBloodAvailabilityFragment extends BaseFragment implements UploadBloodAvailabilityContract.View {

    private static final String KEY_USER_DATA = "user_data";
    @BindView(R.id.donation_type_spinner)
    Spinner donationTypeSpinner;

    @BindView(R.id.blood_type_spinner)
    Spinner bloodTypeSpinner;

    @BindView(R.id.phone_number_edit_text)
    EditText phoneNumberEditText;

    @BindView(R.id.email_edit_text)
    EditText emailEditText;

    @BindView(R.id.first_name_edit_text)
    EditText firstNameEditText;

    @BindView(R.id.last_name_edit_text)
    EditText lastNameEditText;

    @BindView(R.id.religion_spinner)
    Spinner religionSpinner;

    @BindView(R.id.religion_label)
    TextView religionLabel;

    PrefsUtils prefsUtils;

    UserData userData;

    UploadBloodAvailabilityContract.Presenter presenter;

    public UploadBloodAvailabilityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_blood_availability, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle("Upload Blood Availability");
        prefsUtils = Provider.providePrefManager(getContext());
        setDrawerIconToBack();
        prepareBloodGroupSpinner();
        prepareDonationTypeSpinner();

        presenter = new UploadBloodAvailabilityPresenter(this, Provider.provideDataRetrofitService(), prefsUtils, getContext());
        userData = Provider.providePrefManager(getContext()).getPrefAsObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS,
                UserData.class);
        prepopulateFields();
        if (userData.isBloodBank) {
            ViewUtils.hide(religionSpinner, religionLabel);
        } else {
            prepareReligionSpinner();
        }
    }


    void prepopulateFields() {
        phoneNumberEditText.setText(userData.phoneNumber);
        emailEditText.setText(userData.email);
        firstNameEditText.setText(userData.firstName);
        lastNameEditText.setText(userData.lastName);
        //religionSpinner.setText(userData.religion);
    }

    void prepareBloodGroupSpinner() {
        CustomSpinnerAdapter<KeyNameLOVPair> listOfTitleAdapter = new CustomSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, Utils.getBloodGroups());
        bloodTypeSpinner.setAdapter(listOfTitleAdapter);
    }

    void prepareDonationTypeSpinner() {
        CustomSpinnerAdapter<KeyNameLOVPair> listOfTitleAdapter = new CustomSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, Utils.getDonationTypes());
        donationTypeSpinner.setAdapter(listOfTitleAdapter);
    }

    void prepareReligionSpinner() {
        CustomSpinnerAdapter<KeyNameLOVPair> listOfTitleAdapter = new CustomSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, Utils.getReligionList());
        religionSpinner.setAdapter(listOfTitleAdapter);
    }

    @OnClick(R.id.save_button)
    public void onSaveButtonClicked() {
        if (bloodTypeSpinner.getSelectedItemPosition() < 1) {
            showToast("Please select blood type");
            return;
        }

        if (donationTypeSpinner.getSelectedItemPosition() < 1) {
            showToast("Please select donation type");
            return;
        }

        String emailText = emailEditText.getText().toString();

        if (!Utils.isValidEmail(emailText)) {
            emailEditText.setError("Please use a valid email address");
            return;
        }

        if (!ViewUtils.validateEditTexts(firstNameEditText, lastNameEditText, phoneNumberEditText)) {
            return;
        }

        KeyNameLOVPair bloodType = (KeyNameLOVPair) bloodTypeSpinner.getSelectedItem();
        KeyNameLOVPair donationType = (KeyNameLOVPair) donationTypeSpinner.getSelectedItem();
        String religion = "";
        if (religionSpinner.getSelectedItemPosition() > 0) {
            religion = ((KeyNameLOVPair) religionSpinner.getSelectedItem()).key;
        }

        BloodPostingData bloodPostingData = new BloodPostingData(
                bloodType.key,
                donationType.key,
                emailText,
                String.format("%s %s", firstNameEditText.getText().toString(), lastNameEditText.getText().toString()),
                phoneNumberEditText.getText().toString(),
                userData.firebaseID,
                religion,
                null,
                null,
                BloodPostingStatus.PENDING
        );
        presenter.uploadBloodTypeAvailability(bloodPostingData);
    }

    @Override
    public void onBloodTypeAvailabilityUploaded() {
        showSuccessDialog("Blood type availability successfully Added", (dialog, which) -> {
            getFragmentManager().popBackStack();
        });
    }
}

