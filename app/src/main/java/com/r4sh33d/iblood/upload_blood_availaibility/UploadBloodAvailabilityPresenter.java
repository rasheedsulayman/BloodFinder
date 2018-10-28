package com.r4sh33d.iblood.upload_blood_availaibility;

import android.content.Context;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.emailregistration.EmailRegistrationContract;
import com.r4sh33d.iblood.location.LocationUtil;
import com.r4sh33d.iblood.models.BloodSearchData;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.models.UserAuthRequest;
import com.r4sh33d.iblood.models.UserLocation;
import com.r4sh33d.iblood.network.AuthService;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.utils.PrefsUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UploadBloodAvailabilityPresenter implements UploadBloodAvailabilityContract.Presenter {

    private UploadBloodAvailabilityContract.View view;
    private DataService dataService;
    private PrefsUtils prefsUtils;
    private Context context;
    private static final String TAG = UploadBloodAvailabilityPresenter.class.getSimpleName();

    UploadBloodAvailabilityPresenter(UploadBloodAvailabilityContract.View view,
                                     DataService dataService, PrefsUtils prefsUtils, Context context) {
        this.view = view;
        this.dataService = dataService;
        this.prefsUtils = prefsUtils;
        this.context = context;
    }

    @Override
    public void start() {}

    @Override
    public void uploadBloodTypeAvailability(BloodSearchData bloodSearchData) {
        view.showLoading();
        if (!prefsUtils.doesContain(Constants.PREF_KEY_LOCATION_OBJECT)){
            //Okay, Let's try for the last time to retrieve the user's location before we give up
            LocationUtil.getLastKnownLocation(context, new LocationUtil.LocationRetrievedListener() {
                @Override
                public void onLocationRetrievedListener(Location location) {
                    // so location can still be null here, in case of a new phone of factory reset
                    // and other rare cases
                    if (location != null){
                        bloodSearchData.donorsLocation =
                                new UserLocation(location.getLatitude(), location.getLongitude());
                        uploadBloodAvailability(bloodSearchData);
                    }
                }

                @Override
                public void onFailed() {
                    //We give up, Still upload it like that
                    uploadBloodAvailability(bloodSearchData);
                }
            });
        } else {
            //We have the blood type
            UserLocation userLocation = prefsUtils.getPrefAsObject(Constants.PREF_KEY_LOCATION_OBJECT, UserLocation.class);
            bloodSearchData.donorsLocation = userLocation;
            uploadBloodAvailability(bloodSearchData);
        }
    }

    public void uploadBloodAvailability(BloodSearchData bloodSearchData) {
        dataService.saveBloodAvailability(bloodSearchData).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                view.dismissLoading();
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    BloodSearchData data = new Gson().fromJson(jsendResponse.getData(), BloodSearchData.class);
                    view.onBloodTypeAvailabilityUploaded(data);
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
