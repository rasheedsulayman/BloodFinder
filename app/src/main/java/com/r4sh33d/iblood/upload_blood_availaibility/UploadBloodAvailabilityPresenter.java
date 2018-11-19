package com.r4sh33d.iblood.upload_blood_availaibility;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.r4sh33d.iblood.location.LocationUtil;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.MiniLocation;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.utils.PrefsUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class UploadBloodAvailabilityPresenter implements UploadBloodAvailabilityContract.Presenter {

    private UploadBloodAvailabilityContract.View view;
    private DataService dataService;
    private PrefsUtils prefsUtils;
    private Context context;

    UploadBloodAvailabilityPresenter(UploadBloodAvailabilityContract.View view,
                                     DataService dataService, PrefsUtils prefsUtils, Context context) {
        this.view = view;
        this.dataService = dataService;
        this.prefsUtils = prefsUtils;
        this.context = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void uploadBloodTypeAvailability(BloodPostingData bloodPostingData) {
        view.showLoading();
        if (!prefsUtils.doesContain(Constants.PREF_KEY_LOCATION_OBJECT)) {
            //Okay, Let's try for the last time to retrieve the user's location before we give up
            LocationUtil.getLastKnownLocation(context, new LocationUtil.LocationRetrievedListener() {
                @Override
                public void onLocationRetrieved(Location location) {
                    // so location can still be null here, in case of a new phone or factory reset
                    // and other rare cases
                    if (location != null) {
                        MiniLocation miniLocation = new MiniLocation(location.getLatitude(),
                                location.getLongitude());
                        bloodPostingData.donorsLocation = miniLocation;
                        getLocationAddress(miniLocation, bloodPostingData);
                    }
                }

                @Override
                public void onFailed() {
                    //We give up, Still upload it like that
                    uploadBloodAvailability(bloodPostingData);
                }
            });
        } else {
            //We have the location info
            MiniLocation location =  prefsUtils.getPrefAsObject(Constants.PREF_KEY_LOCATION_OBJECT, MiniLocation.class);
            bloodPostingData.donorsLocation = location;
            getLocationAddress(location , bloodPostingData);
        }
    }

    public void getLocationAddress (MiniLocation location , BloodPostingData bloodPostingData){
        Timber.d("Trying to get descriptive Location address: ");
        LocationUtil.getAddressFromLatLongAsync(location,  context , locationAdress -> {
            Timber.d("Location address gotten: " + locationAdress);
            bloodPostingData.donorsLocation.descriptiveAddress = locationAdress;
            uploadBloodAvailability(bloodPostingData);
        });
    }


    public void uploadBloodAvailability(BloodPostingData bloodPostingData) {
        dataService.saveBloodAvailability(bloodPostingData).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    String bloodPostingId = response.body().getAsJsonObject().get("name").getAsString();
                    uploadBloodPostingDataID(bloodPostingId);
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

    public void uploadBloodPostingDataID(String bloodPostingId) {
        dataService.uploadBloodPostingId(bloodPostingId, bloodPostingId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                view.dismissLoading();
                if (!TextUtils.isEmpty(response.body())) {
                    view.onBloodTypeAvailabilityUploaded();
                } else {
                    view.showError("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.dismissLoading();
                view.showError(JsendResponse.ERROR_MESSAGE);
            }
        });
    }

}
