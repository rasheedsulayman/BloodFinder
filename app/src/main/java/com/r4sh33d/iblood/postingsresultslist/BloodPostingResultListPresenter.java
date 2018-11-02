package com.r4sh33d.iblood.postingsresultslist;

import android.app.Notification;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.base.BasePresenter;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.NotificationPayload;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.network.NotificationService;
import com.r4sh33d.iblood.utils.JsendResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BloodPostingResultListPresenter implements BloodPostingResultListContract.Presenter {
    BloodPostingResultListContract.View view;
    NotificationService notificationService;

    public BloodPostingResultListPresenter(BloodPostingResultListContract.View view,
                                           NotificationService notificationService) {
        this.view = view;
        this.notificationService = notificationService;
    }


    @Override
    public void start() {

    }

    @Override
    public void sendNotification(NotificationPayload notificationPayload) {
        view.showLoading();
        notificationService.sendNotification(notificationPayload).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                view.dismissLoading();
                if (response.body().getAsJsonObject().get("success").getAsInt() > 0){
                    //We sent something successfully
                    view.onNotificationSent(notificationPayload);
                }else {
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
}
