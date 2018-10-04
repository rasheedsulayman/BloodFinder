package com.r4sh33d.iblood.login;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.utils.PrefsUtils;
import com.r4sh33d.iblood.models.AdditionalUserDetailsRequest;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.models.UserAuthRequest;
import com.r4sh33d.iblood.network.AccountService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private final AccountService accountService;
    private PrefsUtils prefsUtils;
    private static final String TAG = LoginPresenter.class.getSimpleName();

    LoginPresenter(LoginContract.View view, AccountService accountService , PrefsUtils prefsUtils) {
        this.view = view;
        this.accountService = accountService;
        this.prefsUtils = prefsUtils;
    }

    @Override
    public void start() {}

    @Override
    public void login (UserAuthRequest userAuthRequest) {
        view.showLoading();
        accountService.registerUserEmail(userAuthRequest).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                view.dismissLoading();
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    User user = new Gson().fromJson(jsendResponse.getData(), User.class);
                    getAdditionalUserDetails(user.localId);
                } else {
                    view.showError(jsendResponse.getErrorMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                view.dismissLoading();
                view.showError(JsendResponse.ERROR_MESSAGE);
            }
        });
    }

    public void getAdditionalUserDetails(String userID){
        accountService.getAdditionalUserDetails(userID).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                view.dismissLoading();
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    AdditionalUserDetailsRequest user = new Gson().fromJson(jsendResponse.getData(),
                            AdditionalUserDetailsRequest.class);
                    prefsUtils.putObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS, user);
                    view.onUserSuccessfullyLoggedIn(user);
                } else {
                    view.showError(jsendResponse.getErrorMessage());
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                view.dismissLoading();
                view.showError(JsendResponse.ERROR_MESSAGE);
            }
        });
    }


}
