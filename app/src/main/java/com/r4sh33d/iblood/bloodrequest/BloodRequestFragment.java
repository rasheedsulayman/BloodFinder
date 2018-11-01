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
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.postingsresultslist.BloodPostingResultListFragment;
import com.r4sh33d.iblood.utils.CustomSpinnerAdapter;
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

    @BindView(R.id.religion_edit_text)
    EditText religionEditText;

    @BindView(R.id.consider_religion_radio_group)
    RadioGroup considerReligionRadioGroup;

    @BindView(R.id.religion_label)
    TextView religionLabel;

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
        setToolbarTitle("Search donors");
        setDrawerIconToBack();
        prepareRadioGroup();
        presenter = new BloodRequestPresenter(this, Provider.provideDataRetrofitService(), getContext());
    }

    void prepareRadioGroup() {
        considerReligionRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.yes_consider_religion:
                    ViewUtils.show(religionEditText, religionLabel);
                    break;
                case R.id.no_dont_consider_religion:
                    ViewUtils.hide(religionLabel, religionEditText);
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

    @OnClick(R.id.save_button)
    public void onSaveButtonClicked() {
        if (bloodGroupSpinner.getSelectedItemId() < 1) {
            showToast("Please select blood type");
            return;
        }

        if (donationTypeSpinner.getSelectedItemId() < 1) {
            showToast("Please select donation type");
            return;
        }

        boolean considerRelogion = considerReligionRadioGroup.getCheckedRadioButtonId() == R.id.yes_consider_religion;
        if (considerRelogion && !ViewUtils.validateEditTexts(religionEditText)) {
            return;
        }

        KeyNameLOVPair bloodType = (KeyNameLOVPair) bloodGroupSpinner.getSelectedItem();
        KeyNameLOVPair donationType = (KeyNameLOVPair) donationTypeSpinner.getSelectedItem();
        BloodSearchData bloodSearchData = new BloodSearchData(bloodType.key, donationType.key,considerRelogion,
                religionEditText.getText().toString());

        presenter.requestForBlood(bloodSearchData);
    }

    @Override
    public void onDonorsPostingsFetched(ArrayList<BloodPostingData> resultsList) {
          replaceFragment(BloodPostingResultListFragment.newInstance(resultsList));
    }
}
