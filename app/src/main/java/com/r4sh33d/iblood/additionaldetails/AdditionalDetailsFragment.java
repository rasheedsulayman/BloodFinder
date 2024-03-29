package com.r4sh33d.iblood.additionaldetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.r4sh33d.iblood.dashboard.DashboardActivity;
import com.r4sh33d.iblood.utils.CustomSpinnerAdapter;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.models.KeyNameLOVPair;
import com.r4sh33d.iblood.utils.Utils;
import com.r4sh33d.iblood.utils.ViewUtils;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.UserData;
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
    @BindView(R.id.first_name_edit_text)
    EditText firstNameEditText;
    @BindView(R.id.last_name_edit_text)
    EditText lastNameEditText;
    @BindView(R.id.religion_spinner)
    Spinner religionSpinner;
    @BindView(R.id.religion_label)
    TextView religionLabel;
    @BindView(R.id.email_edit_text)
    EditText emailEditText;
    @BindView(R.id.address_edit_text)
    EditText addressEditText;

    @BindView(R.id.user_type_spinner)
    Spinner userTypeSpinner;

    public AdditionalDetailsFragment() {
        // Required empty public constructor
    }

    public static AdditionalDetailsFragment newInstance(User user) {
        Bundle args = new Bundle();
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
                Provider.provideRetrofitService(Provider.provideDataRetrofitInstance(), DataService.class),
                Provider.providePrefManager(getContext()));
        user = getArguments().getParcelable(KEY_USER);
        prepareSpinner();
    }

    void prepareSpinner() {
        CustomSpinnerAdapter<KeyNameLOVPair> listOfTitleAdapter = new CustomSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, Utils.getUserTypesList());
        userTypeSpinner.setAdapter(listOfTitleAdapter);
        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1: //Individual user
                        ViewUtils.show(religionSpinner, religionLabel);
                        break;
                    case 2: //Blood bank
                        ViewUtils.hide(religionSpinner, religionLabel);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomSpinnerAdapter<KeyNameLOVPair> listOfReligionAdapter = new CustomSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, Utils.getReligionList());
        religionSpinner.setAdapter(listOfReligionAdapter);
    }

    @OnClick(R.id.save_button)
    public void onSaveButtonClicked() {
        if (userTypeSpinner.getSelectedItemPosition() < 1) {
            showToast("Please select user type");
            return;
        }
        String emailText = emailEditText.getText().toString();
        if (!Utils.isValidEmail(emailText)) {
            emailEditText.setError("Please use a valid email address");
            return;
        }

        if (!ViewUtils.validateEditTexts(firstNameEditText, lastNameEditText, addressEditText)) {
            return;
        }

        boolean isBloodBank = ((KeyNameLOVPair) userTypeSpinner.getSelectedItem()).key.equals("blood_bank");
        String religion = "";

        if ((ViewUtils.isShowing(religionSpinner) && religionSpinner.getSelectedItemPosition() > 0)){
            religion = ((KeyNameLOVPair) religionSpinner.getSelectedItem()).key;
        }

        UserData userData = new UserData(
                isBloodBank,
                firstNameEditText.getText().toString(),
                lastNameEditText.getText().toString(),
                emailText,
                user.email.substring(0, 11), //Users email is in the format of [phonenumber]@iblood-7253a.firebaseio.com
                addressEditText.getText().toString(),
                religion,
                user.localId
        );

        presenter.saveAdditionalDetails(userData);
    }

    @Override
    public void onAdditionalDetailsSavedSuccessfully(UserData user) {
        //We are done we can now go back and login
        showSuccessDialog("Account successfully created",
                (dialog, which) -> getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE));
    }
}