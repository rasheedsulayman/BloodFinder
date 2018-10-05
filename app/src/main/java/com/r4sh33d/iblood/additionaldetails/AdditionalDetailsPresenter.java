package com.r4sh33d.iblood.additionaldetails;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.utils.PrefsUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdditionalDetailsPresenter implements AdditionalDetailsContract.Presenter {

    private AdditionalDetailsContract.View view;
    private final DataService dataService;
    private final PrefsUtils prefsUtils;
    private static final String TAG = AdditionalDetailsPresenter.class.getSimpleName();

    AdditionalDetailsPresenter(AdditionalDetailsContract.View view, DataService dataService,
                               PrefsUtils prefsUtils) {
        this.view = view;
        this.dataService = dataService;
        this.prefsUtils = prefsUtils;
    }

    @Override
    public void start() {
    }

    @Override
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
    }
}
