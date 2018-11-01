package com.r4sh33d.iblood.dashboard;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.models.UserAuthRequest;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.AuthService;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.utils.PrefsUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardPresenter implements DashboardContract.Presenter {

    private DashboardContract.View view;
    private DataService dataService;
    private PrefsUtils prefsUtils;
    private static final String TAG = DashboardPresenter.class.getSimpleName();

    DashboardPresenter(DashboardContract.View view, DataService dataService, PrefsUtils prefsUtils) {
        this.view = view;
        this.dataService = dataService;
        this.prefsUtils = prefsUtils;
    }

    @Override
    public void start() {
    }

    @Override
    public void subScribeNotification(String refreshedToken, UserData userdata) {
        dataService.updateUserNotificationToken(userdata.firebaseID, refreshedToken).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //This validation is painful, Different firebase nodes return different structures
                if (!TextUtils.isEmpty(response.toString())) {
                    prefsUtils.putBoolean(Constants.PREF_KEY_IS_NOTIFICATION_SUBSCRIBED, true);
                }else {
                    // we failed to register or refresh
                    // try again when user opens app another time
                    prefsUtils.putBoolean(Constants.PREF_KEY_IS_NOTIFICATION_SUBSCRIBED, false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                prefsUtils.putBoolean(Constants.PREF_KEY_IS_NOTIFICATION_SUBSCRIBED, false);
            }
        });
    }
}
