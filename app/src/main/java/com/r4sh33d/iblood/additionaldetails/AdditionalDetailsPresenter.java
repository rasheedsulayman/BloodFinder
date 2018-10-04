package com.r4sh33d.iblood.additionaldetails;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.models.AdditionalUserDetailsRequest;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.network.AccountService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdditionalDetailsPresenter implements AdditionalDetailsContract.Presenter {

    private AdditionalDetailsContract.View view;
    private final AccountService accountService;
    private static final String TAG = AdditionalDetailsPresenter.class.getSimpleName();

    AdditionalDetailsPresenter(AdditionalDetailsContract.View view, AccountService accountService) {
        this.view = view;
        this.accountService = accountService;
    }

    @Override
    public void start() {}

    @Override
    public void saveAdditionalDetails(AdditionalUserDetailsRequest additionalUserDetailsRequest) {
        view.showLoading();
        accountService.saveAdditionalUserDetail(additionalUserDetailsRequest,
                additionalUserDetailsRequest.firebaseID).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                view.dismissLoading();
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    User user = new Gson().fromJson(jsendResponse.getData(), User.class);
                    view.onAdditionalDetailsSavedSuccessfuly(user);
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
