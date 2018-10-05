package com.r4sh33d.iblood.emailregistration;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.network.AuthService;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.models.UserAuthRequest;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.utils.PrefsUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmailRegistrationPresenter implements EmailRegistrationContract.Presenter {

    private EmailRegistrationContract.View view;
    private AuthService authService;
    private static final String TAG = EmailRegistrationPresenter.class.getSimpleName();

    EmailRegistrationPresenter(EmailRegistrationContract.View view, AuthService authService ) {
        this.view = view;
        this.authService = authService;

    }

    @Override
    public void start() {}

    @Override
    public void registerUserEmail(UserAuthRequest userAuthRequest) {
        view.showLoading();
        authService.registerUserEmail(userAuthRequest).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                view.dismissLoading();
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    User user = new Gson().fromJson(jsendResponse.getData(), User.class);
                    view.onUserEmailRegistered(user);
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
