package com.r4sh33d.iblood.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.r4sh33d.iblood.dashboard.DashboardActivity;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.emailregistration.EmailRegistrationFragment;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.models.UserAuthRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {

    @BindView(R.id.phone_number_edit_text)
    EditText phoneNumberEditText;

    @BindView(R.id.password_edit_text)
    EditText passwordEditText;


    LoginContract.Presenter presenter;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideToolbar();
        presenter = new LoginPresenter(this,
                Provider.provideDataRetrofitService(), Provider.provideAuthRetrofitService(),
                Provider.providePrefManager(getContext()));
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        String phoneNumber = phoneNumberEditText.getText().toString();
        String firstPassword = passwordEditText.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberEditText.setError("Please enter valid number");
            return;
        }

        if (firstPassword.length() < 6) {
            passwordEditText.setError("Password must be at least six characters long");
            return;
        }

        UserAuthRequest userAuthRequest = new UserAuthRequest(phoneNumber.concat(Constants.phoneNumberSuffix),
                firstPassword);
        presenter.login(userAuthRequest);
    }

    @OnClick(R.id.click_here_to_register_textview)
    public void registerTextViewClicked() {
        replaceFragment(new EmailRegistrationFragment());
    }


    @Override
    public void onUserSuccessfullyLoggedIn(UserData user) {
        Intent intent = new Intent(getContext(), DashboardActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}