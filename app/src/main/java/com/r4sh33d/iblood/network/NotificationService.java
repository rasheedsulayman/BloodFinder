package com.r4sh33d.iblood.network;

import com.google.gson.JsonElement;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.NotificationPayload;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NotificationService {

    @POST("/fcm/send")
    Call<JsonElement> sendNotification (@Body NotificationPayload notificationPayload);
}
