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

import com.r4sh33d.iblood.DrawerActivity;
import com.r4sh33d.iblood.Provider;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.utils.Utils;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.AdditionalUserDetailsRequest;
import com.r4sh33d.iblood.models.UserAuthRequest;
import com.r4sh33d.iblood.network.AccountService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements  LoginContract.View {

    @BindView(R.id.email_edit_text)
    EditText emailEditText;

    @BindView(R.id.password_edittext)
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
        presenter = new LoginPresenter(this,
                Provider.provideRetrofitService(Provider.provideAuthRetrofitInstance(), AccountService.class),
                Provider.providePrefManager(getContext()));
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClicked(){
        String emailAddress = emailEditText.getText().toString();
        String firstPassword = passwordEditText.getText().toString();
        if (Utils.isValidEmail(emailAddress)) {
            emailEditText.setError("Please use a valid email address");
            return;
        }
        if ((TextUtils.isEmpty(firstPassword))) {
            passwordEditText.setError("Password is empty");
            return;
        }

        UserAuthRequest userAuthRequest = new UserAuthRequest(emailAddress, firstPassword);
        presenter.login(userAuthRequest);
    }

    @OnClick(R.id.click_here_to_register_textview)
    public void registerTextViewClicked() {

    }


    @Override
    public void onUserSuccessfullyLoggedIn(AdditionalUserDetailsRequest user) {
        Intent intent = new Intent(getContext() , DrawerActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
