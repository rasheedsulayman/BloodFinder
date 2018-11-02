package com.r4sh33d.iblood.notification.acceptancedetails;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.models.AcceptanceNotificationData;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.NotificationPayload;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.network.NotificationService;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.utils.PrefsUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AcceptanceDetailsPresenter implements AcceptanceDetailsContract.Presenter {

    private AcceptanceDetailsContract.View view;
    private final DataService dataService;
    private final PrefsUtils prefsUtils;
    private NotificationService notificationService;

    AcceptanceDetailsPresenter(AcceptanceDetailsContract.View view, DataService dataService,
                               PrefsUtils prefsUtils) {
        this.view = view;
        this.dataService = dataService;
        this.prefsUtils = prefsUtils;
        this.notificationService = notificationService;
    }

    @Override
    public void fetchDetails(AcceptanceNotificationData notificationData) {
        view.showLoading("Fetching the requester details");
        dataService.getAdditionalUserDetails(notificationData.bloodDonorFbId).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    UserData bloodDonorData = new Gson().fromJson(jsendResponse.getData(), UserData.class);
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


    /*void fetchBloodPostingData(String blodPostingID, UserData bloodSeeker) {
        dataService.getBloodPosting(blodPostingID).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                view.dismissLoading();
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    BloodPostingData bloodPostingData = new Gson().fromJson(jsendResponse.getData(), BloodPostingData.class);
                    view.onDetailsSuccessfullyFetched(bloodSeeker, bloodPostingData);
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
