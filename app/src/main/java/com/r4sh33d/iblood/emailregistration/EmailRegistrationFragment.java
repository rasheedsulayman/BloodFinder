package com.r4sh33d.iblood.emailregistration;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.r4sh33d.iblood.additionaldetails.AdditionalDetailsFragment;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.models.UserAuthRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmailRegistrationFragment extends BaseFragment implements EmailRegistrationContract.View {

    private static final String KEY_IS_BLOOD_BANK = "isbloodbank";
    @BindView(R.id.phone_number_edit_text)
    EditText phoneNumberEditText;

    @BindView(R.id.password_edittext)
    EditText passwordEditText;

    @BindView(R.id.confirm_password_edittext)
    EditText confirmPasswordEditText;

    @BindView(R.id.register_button)
    Button registerButton;

    EmailRegistrationContract.Presenter presenter;


    public static EmailRegistrationFragment newInstance(Boolean isBloodBank) {
        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_BLOOD_BANK, isBloodBank);
        EmailRegistrationFragment fragment = new EmailRegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EmailRegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_email_registration, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar();
        setToolbarTitle("Register");
        presenter = new EmailRegistrationPresenter(this, Provider.provideAuthRetrofitService());
    }

    @Override
    public void onUserEmailRegistered(User user) {
        replaceFragment(AdditionalDetailsFragment.newInstance(user));
    }

    @OnClick(R.id.register_button)
    public void onRegisterButtonClicked() {
        String phoneNumber = phoneNumberEditText.getText().toString();
        String firstPassword = passwordEditText.getText().toString();
        String secondPassword = confirmPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberEditText.setError("Please use a valid phone number");
            return;
        }

        if ((TextUtils.isEmpty(firstPassword) || TextUtils.isEmpty(secondPassword))) {
            showToast("Please select a valid password");
            return;
        }

        if (firstPassword.length() < 6) {
            passwordEditText.setError("Password must be at least six characters long");
            return;
        }

        if (!firstPassword.equals(secondPassword)) {
            showToast("Passwords do not match");
            return;
        }

        UserAuthRequest userAuthRequest = new UserAuthRequest(phoneNumber.concat(Constants.phoneNumberSuffix), firstPassword);
        presenter.registerUserEmail(userAuthRequest);
    }
}
