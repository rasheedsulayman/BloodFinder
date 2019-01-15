package com.r4sh33d.iblood.login;


import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.r4sh33d.iblood.dashboard.DashboardActivity;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.emailregistration.EmailRegistrationFragment;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.models.UserAuthRequest;
import com.r4sh33d.iblood.utils.PrefsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {

    @BindView(R.id.phone_number_edit_text)
    EditText phoneNumberEditText;

    @BindView(R.id.password_edit_text)
    EditText passwordEditText;

    LoginContract.Presenter presenter;
    Task<LocationSettingsResponse> task;

    private boolean isLocationServiceEnabled;

    private static final int REQUEST_CHECK_SETTINGS = 100;

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
        prepopulateData();
        hideKeyboard();
    }

    void prepopulateData(){
        PrefsUtils prefsUtils = Provider.providePrefManager(getContext());
        if (prefsUtils.doesContain(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS)){
            UserData userData = prefsUtils.getPrefAsObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS, UserData.class);
            phoneNumberEditText.setText(userData.phoneNumber);
        }
    }



    protected void createLocationRequest() {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        showLoading("Checking location services availability");
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(getActivity());

        task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(getActivity(), locationSettingsResponse -> {
            dismissLoading();
            isLocationServiceEnabled = true;
            login();
        });

        task.addOnFailureListener(getActivity(), e -> {
            dismissLoading();
            isLocationServiceEnabled = false;
            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(getActivity(),
                            REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d("onActivity result: " + data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            isLocationServiceEnabled = resultCode == RESULT_OK;
        }
    }


    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        login();
    }

    void login(){
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

        if (isLocationServiceEnabled){
            presenter.login(userAuthRequest);
        }else {
            createLocationRequest();
        }
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