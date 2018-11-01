package com.r4sh33d.iblood.notification.services;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.utils.PrefsUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationIDService extends FirebaseInstanceIdService {
    PrefsUtils prefsUtils;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        prefsUtils = Provider.providePrefManager(this);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        prefsUtils.putString(Constants.PREF_KEY_NOTIFICATION_TOKEN, refreshedToken);
        if (prefsUtils.doesContain(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS)) {
            subScribeNotification(refreshedToken);
        }
    }

    public void subScribeNotification(String refreshedToken) {
        UserData user = new Gson().fromJson(prefsUtils.getString(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS, ""),
                UserData.class);
        DataService dataService = Provider.provideDataRetrofitService();
        dataService.updateUserNotificationToken(user.firebaseID, refreshedToken).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsendResponse jsendResponse = new JsendResponse(response.body(),
                        response.errorBody());
                if (jsendResponse.isSuccess()) {
                    if (jsendResponse.getData().getAsBoolean()) {
                        prefsUtils.putBoolean(Constants.PREF_KEY_IS_NOTIFICATION_SUBSCRIBED, true);
                    }
                } else {
                     // we failed to register or refresh
                     // try again when user opens app
                    prefsUtils.putBoolean(Constants.PREF_KEY_IS_NOTIFICATION_SUBSCRIBED, false);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
}
