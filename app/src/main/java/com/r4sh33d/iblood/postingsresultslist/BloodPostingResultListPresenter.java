package com.r4sh33d.iblood.postingsresultslist;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.NotificationPayload;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.network.NotificationService;
import com.r4sh33d.iblood.utils.JsendResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class BloodPostingResultListPresenter implements BloodPostingResultListContract.Presenter {
    BloodPostingResultListContract.View view;
    NotificationService notificationService;
    private DataService dataService;

    public BloodPostingResultListPresenter(BloodPostingResultListContract.View view,
                                           NotificationService notificationService, DataService dataService) {
        this.view = view;
        this.notificationService = notificationService;
        this.dataService = dataService;
    }


    @Override
    public void start() {

    }

    @Override
    public void fetchMoreDetails(BloodPostingData bloodPostingData) {
        view.showLoading("Fetching the donor details");
        dataService.getAdditionalUserDetails(bloodPostingData.donorsFirebaseId).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    UserData bloodDonorData = new Gson().fromJson(jsendResponse.getData(), UserData.class);
                    view.onDetailsSuccessfullyFetched(bloodDonorData, bloodPostingData);
                } else {
                    view.dismissLoading();
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
    public void sendNotification(UserData bloodSeekerData, BloodPostingData bloodPostingData,
                                 NotificationPayload<BloodRequestNotificationData> notificationPayload) {
        view.showLoading("Sending request, Please wait. . .");
        notificationService.sendNotification(notificationPayload).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Timber.d("The response is : %s", response);
                if (response.body() != null && response.body().getAsJsonObject().get("success").getAsInt() > 0) {
                    //We sent something successfully
                    updateUsersRequestHistory(bloodSeekerData, bloodPostingData.id, bloodPostingData);

                } else {
                    view.dismissLoading();
                    //something went wrong, NoOp for now
                    //TODO comeback and handle this
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                view.dismissLoading();
                view.showError(JsendResponse.ERROR_MESSAGE);
            }
        });
    }

    void updateUsersRequestHistory(UserData bloodSeekerData, String bloodPostingId, BloodPostingData bloodPostingData) {
        bloodPostingData.creationTime = String.valueOf(System.currentTimeMillis());
        dataService.updateUserRequestHistory(bloodSeekerData.firebaseID, bloodPostingId, bloodPostingData).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                view.dismissLoading();
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    view.onNotificationSent();
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
