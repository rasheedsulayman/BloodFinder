package com.r4sh33d.iblood.additionaldetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.r4sh33d.iblood.CustomSpinnerAdapter;
import com.r4sh33d.iblood.DrawerActivity;
import com.r4sh33d.iblood.Provider;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.models.UserType;
import com.r4sh33d.iblood.utils.Utils;
import com.r4sh33d.iblood.utils.ViewUtils;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.AdditionalUserDetailsRequest;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.network.DataService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */

public class AdditionalDetailsFragment extends BaseFragment implements AdditionalDetailsContract.View {
    private static final String KEY_IS_BLOOD_BANK = "isbloodbank";
    private static final String KEY_USER = "user_key";
    boolean isBloodBank;
    User user;
    AdditionalDetailsContract.Presenter presenter;

    @BindView(R.id.name_edit_text)
    EditText nameEditText;
    @BindView(R.id.religion_edit_text)
    EditText religionEditText;
    @BindView(R.id.phone_number_edit_text)
    EditText phoneNumberEditText;
    @BindView(R.id.address_edit_text)
    EditText addressEditText;


    @BindView(R.id.spinner)
    Spinner spinner;

    public AdditionalDetailsFragment() {
        // Required empty public constructor
    }

    public static AdditionalDetailsFragment newInstance(Boolean isBloodBank, User user) {
        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_BLOOD_BANK, isBloodBank);
        args.putParcelable(KEY_USER, user);
        AdditionalDetailsFragment fragment = new AdditionalDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_additional_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle("Additional Information");
        presenter = new AdditionalDetailsPresenter(this,
                Provider.provideRetrofitService(Provider.provideDataRetrofitInstance(), DataService.class));
        isBloodBank = getArguments().getBoolean(KEY_IS_BLOOD_BANK);
        user = getArguments().getParcelable(KEY_USER);
        prepareSpinner();

    }


    void prepareSpinner() {
        CustomSpinnerAdapter<UserType> listOfTitleAdapter = new CustomSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, Utils.getUserTypesList());
        spinner.setAdapter(listOfTitleAdapter);
    }

    @OnClick(R.id.save_button)
    public void onSaveButtonClicked() {
        if (!ViewUtils.validateEditTexts(nameEditText, phoneNumberEditText, addressEditText)) {
            return;
        }

        AdditionalUserDetailsRequest additionalUserDetailsRequest = new AdditionalUserDetailsRequest(
                isBloodBank,
                nameEditText.getText().toString(),
                user.email,
                phoneNumberEditText.getText().toString(),
                addressEditText.getText().toString(),
                religionEditText.getText().toString(),
                user.localId
        );
        presenter.saveAdditionalDetails(additionalUserDetailsRequest);
    }

    @Override
    public void onAdditionalDetailsSavedSuccessfuly(User user) {
        //We are done we can now go back and login
        showSuccessDialog("Account successfully created", (dialog, which) -> {
            //We can either launch the Dashboard or go back to login.
            startActivity(new Intent(getContext() , DrawerActivity.class));
            getActivity().finish();
        });

    }
}