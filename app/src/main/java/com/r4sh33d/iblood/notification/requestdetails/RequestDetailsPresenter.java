package com.r4sh33d.iblood.notification.requestdetails;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.models.AcceptanceNotificationData;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.BloodSearchData;
import com.r4sh33d.iblood.models.NotificationPayload;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.network.NotificationService;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.utils.PrefsUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestDetailsPresenter implements RequestDetailsContract.Presenter {

    private RequestDetailsContract.View view;
    private final DataService dataService;
    private final PrefsUtils prefsUtils;
    private NotificationService notificationService;

    RequestDetailsPresenter(RequestDetailsContract.View view, DataService dataService,
                            PrefsUtils prefsUtils, NotificationService notificationService) {
        this.view = view;
        this.dataService = dataService;
        this.prefsUtils = prefsUtils;
        this.notificationService = notificationService;
    }

    @Override
    public void fetchDetails(BloodRequestNotificationData notificationData) {
        view.showLoading("Fetching the requester details");
        dataService.getAdditionalUserDetails(notificationData.bloodSeekerFbId).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    UserData bloodSeekerData = new Gson().fromJson(jsendResponse.getData(), UserData.class);
                    fetchBloodPostingData(notificationData.bloodPostingId, bloodSeekerData);
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
    public void sendNotification( UserData bloodSeekerData, NotificationPayload<AcceptanceNotificationData> notificationPayload) {
        view.showLoading("Sending Notification");
        notificationService.sendNotification(notificationPayload).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.body().getAsJsonObject().get("success").getAsInt() > 0) {
                    //We sent something successfully
                    updateBloodPosting(bloodSeekerData , notificationPayload.data.bloodPostingId);
                } else {
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

    void updateBloodPosting(UserData bloodSeekerData, String bloodPostingId ){
        HashMap<String, String> payload = new HashMap<>();
        payload.put("acceptance_status" , "accepted");
        payload.put("accepted_seeker_id", bloodSeekerData.firebaseID);
        dataService.updateBloodPosting(bloodPostingId , payload).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                view.dismissLoading();
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    view.onNotificationSuccessfullySent();
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


    void fetchBloodPostingData(String blodPostingID, UserData bloodSeeker) {
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
    }
}
