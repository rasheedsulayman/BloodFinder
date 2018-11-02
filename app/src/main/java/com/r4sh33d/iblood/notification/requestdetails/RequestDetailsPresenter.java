package com.r4sh33d.iblood.notification.requestdetails;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.utils.PrefsUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestDetailsPresenter implements RequestDetailsContract.Presenter {

    private RequestDetailsContract.View view;
    private final DataService dataService;
    private final PrefsUtils prefsUtils;

    RequestDetailsPresenter(RequestDetailsContract.View view, DataService dataService,
                            PrefsUtils prefsUtils) {
        this.view = view;
        this.dataService = dataService;
        this.prefsUtils = prefsUtils;
    }

    @Override
    public void fetchSeekersDetails(String seekerFbId) {
        view.showLoading("Fetching the requester details");
        dataService.getAdditionalUserDetails(seekerFbId).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                view.dismissLoading();
                if (jsendResponse.isSuccess()) {
                    UserData user = new Gson().fromJson(jsendResponse.getData(), UserData.class);
                    view.onDetailsSuccessfullyFetched(user);
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

    @Override
    public void fetchBloodPosting(String bloodPostingId) {

    }

  /*  @Override
    public void saveAdditionalDetails(UserData userData) {
        view.showLoading();
        dataService.saveAdditionalUserDetail(userData,
                userData.firebaseID).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                view.dismissLoading();
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    UserData userDetails = new Gson().fromJson(jsendResponse.getData(), UserData.class);
                    prefsUtils.putObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS, userDetails);
                    view.onAdditionalDetailsSavedSuccessfully(userDetails);
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
    }*/
}
