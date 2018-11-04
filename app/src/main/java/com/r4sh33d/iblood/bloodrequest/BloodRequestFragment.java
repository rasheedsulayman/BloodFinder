package com.r4sh33d.iblood.bloodrequest;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodSearchData;
import com.r4sh33d.iblood.models.KeyNameLOVPair;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.postingsresultslist.BloodPostingResultListFragment;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.CustomSpinnerAdapter;
import com.r4sh33d.iblood.utils.PrefsUtils;
import com.r4sh33d.iblood.utils.Utils;
import com.r4sh33d.iblood.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class BloodRequestFragment extends BaseFragment implements BloodRequestContract.View {

    @BindView(R.id.donation_type_spinner)
    Spinner donationTypeSpinner;

    @BindView(R.id.blood_group_spinner)
    Spinner bloodGroupSpinner;

    @BindView(R.id.religion_spinner)
    Spinner religionSpinner;

    @BindView(R.id.consider_religion_radio_group)
    RadioGroup considerReligionRadioGroup;

    @BindView(R.id.religion_label)
    TextView religionLabel;

    @BindView(R.id.consider_donor_religion_label)
    TextView considerDonorReligionLabel;
    UserData userData;

    BloodRequestContract.Presenter presenter;


    public BloodRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blood_request, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareDonationTypeSpinner();
        prepareBloodGroupSpinner();
        prepareReligionSpinner();
        setToolbarTitle("Search donors");
        setDrawerIconToBack();
        presenter = new BloodRequestPresenter(this, Provider.provideDataRetrofitService(), getContext());
        prepareRadioGroup();
        PrefsUtils prefsUtils = Provider.providePrefManager(getContext());
        userData = prefsUtils.getPrefAsObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS, UserData.class);
        if (userData.isBloodBank) {
            ViewUtils.hide(considerReligionRadioGroup, considerDonorReligionLabel, religionLabel, religionSpinner);
        }

    }

    void prepareRadioGroup() {
        considerReligionRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.yes_consider_religion:
                    ViewUtils.show(religionSpinner, religionLabel);
                    break;
                case R.id.no_dont_consider_religion:
                    ViewUtils.hide(religionLabel, religionSpinner);
                    break;
            }
        });
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

    void prepareReligionSpinner() {
        CustomSpinnerAdapter<KeyNameLOVPair> listOfTitleAdapter = new CustomSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, Utils.getReligionList());
        religionSpinner.setAdapter(listOfTitleAdapter);
    }


    @OnClick(R.id.save_button)
    public void onSaveButtonClicked() {
        if (bloodGroupSpinner.getSelectedItemPosition() < 1) {
            showToast("Please select blood type");
            return;
        }

        if (donationTypeSpinner.getSelectedItemPosition() < 1) {
            showToast("Please select donation type");
            return;
        }


        boolean considerReligion = considerReligionRadioGroup.getCheckedRadioButtonId() == R.id.yes_consider_religion;
        String religion = "";
        if (!userData.isBloodBank && considerReligion && !(religionSpinner.getSelectedItemPosition() > 0)) {
            showToast("Please select a valid religion");
            return;
        }

        if (religionSpinner.getSelectedItemPosition() > 0) {
            religion = ((KeyNameLOVPair) donationTypeSpinner.getSelectedItem()).key;
        }

        KeyNameLOVPair bloodType = (KeyNameLOVPair) bloodGroupSpinner.getSelectedItem();
        KeyNameLOVPair donationType = (KeyNameLOVPair) donationTypeSpinner.getSelectedItem();
        BloodSearchData bloodSearchData = new BloodSearchData(bloodType.key, donationType.key, considerReligion,
                religion);
        presenter.requestForBlood(bloodSearchData);
    }

    @Override
    public void onDonorsPostingsFetched(ArrayList<BloodPostingData> resultsList) {
        replaceFragment(BloodPostingResultListFragment.newInstance(resultsList));
    }
}
