package com.r4sh33d.iblood;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.models.KeyNameLOVPair;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.CustomSpinnerAdapter;
import com.r4sh33d.iblood.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadBloodAvailabilityFragment extends BaseFragment {
    private static final String KEY_USER_DATA = "user_data";
    @BindView(R.id.donation_type_spinner)
    Spinner donationTypeSpinner;

    @BindView(R.id.blood_group_spinner)
    Spinner bloodGroupSpinner;

    @BindView(R.id.phone_number_edit_text)
    EditText phoneNumberEditText;

    @BindView(R.id.email_edit_text)
    EditText emailEditText;

    @BindView(R.id.name_edit_text)
    EditText nameEditText;

    @BindView(R.id.religion_edit_text)
    EditText religionEditText;

    UserData userData;

    public static UploadBloodAvailabilityFragment newInstance() {
        Bundle args = new Bundle();
       // args.putParcelable(KEY_USER_DATA , userData);
        UploadBloodAvailabilityFragment fragment = new UploadBloodAvailabilityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public UploadBloodAvailabilityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_upload_blood_availability, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle("Upload Blood Availability");
        setDrawerIconToBack();
        prepareBloodGroupSpinner();
        prepareDonationTypeSpinner();
        userData  = Provider.providePrefManager(getContext()).getPrefAsObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS,
                UserData.class);
        prepopulateFields();
    }

    void prepopulateFields(){
        phoneNumberEditText.setText(userData.phoneNumber);
        emailEditText.setText(userData.email);
        nameEditText.setText(userData.name);
        religionEditText.setText(userData.name);
    }

    void prepareBloodGroupSpinner() {
        CustomSpinnerAdapter<KeyNameLOVPair> listOfTitleAdapter = new CustomSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, Utils.getBloodGroups());
        bloodGroupSpinner.setAdapter(listOfTitleAdapter);
    }

    void prepareDonationTypeSpinner() {
        CustomSpinnerAdapter<KeyNameLOVPair> listOfTitleAdapter = new CustomSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, Utils.getDonationTypes());
        donationTypeSpinner.setAdapter(listOfTitleAdapter);
    }
}
