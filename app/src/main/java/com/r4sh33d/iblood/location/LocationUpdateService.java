package com.r4sh33d.iblood.location;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.models.UserLocation;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.utils.PrefsUtils;
import com.r4sh33d.iblood.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LocationUpdateService extends Service {
    LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    public static final int UPDATE_INTERVAL = 5 * 60 * 60 * 1000; // 5 hours
    public static final int FASTEST_UPDATE_INTERVAL = 4 * 60 * 60 * 1000; // 4hours
    PrefsUtils prefsUtils;
    public static final String GET_LAST_KNOWN_LOCATION = Constants.PACKAGE_NAME + "get_last_known_location";

    @SuppressLint("MissingPermission")
    // We are checking it with Utils.isLocationPermissionEnabled(this)
    @Override
    public void onCreate() {
        super.onCreate();

        if (!Utils.isLocationPermissionEnabled(this)) {
            return;
        }

        prefsUtils = Provider.providePrefManager(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        getLastKnownLocation();
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) { //This intent can be null if we are restarting from START_STICKY
            handleCommandIntent(intent);
        }
        return START_STICKY; //Wanna restart when the system kills the service
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @SuppressLint("MissingPermission")
    void getLastKnownLocation() {
        if (!Utils.isLocationPermissionEnabled(this)) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    //As a start, get the user's last Known location
                    if (location != null) {
                        UserLocation userLocation = new UserLocation(location.getLatitude(), location.getLongitude());
                        prefsUtils.putObject(Constants.PREF_KEY_LOCATION_OBJECT,
                                userLocation);
                        LocationUtil.getAddressFromLatLongAsync(userLocation, LocationUpdateService.this, locationAdress -> {
                            userLocation.descriptiveAddress = locationAdress;
                            saveUserLocation(userLocation);
                        });
                        Timber.d("Got last know location, long: %s, Lat: %s",
                                location.getLongitude(), location.getLatitude());
                    }
                }).addOnFailureListener(e -> {
            //We failed
            Timber.e(e, "Failed to get location:");
        });
    }

    private void handleCommandIntent(Intent intent) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case GET_LAST_KNOWN_LOCATION:
                    getLastKnownLocation();
                    break;
                default:
                    break;
            }
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location lastLocation = locationResult.getLastLocation();
            UserLocation userLocation = new UserLocation(lastLocation.getLatitude(), lastLocation.getLongitude());
            prefsUtils.putObject(Constants.PREF_KEY_LOCATION_OBJECT, userLocation);
            LocationUtil.getAddressFromLatLongAsync(userLocation, LocationUpdateService.this, locationAdress -> {
                userLocation.descriptiveAddress = locationAdress;
                saveUserLocation(userLocation);
            });
            Timber.d("Got a location result: %s", locationResult.getLastLocation());
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    public void saveUserLocation(UserLocation userLocation) {
        if (prefsUtils.doesContain(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS)) {
            UserData userData = prefsUtils.getPrefAsObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS, UserData.class);
            DataService dataService = Provider.provideDataRetrofitService();
            dataService.updateUserLocation(userData.firebaseID, userLocation).enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                    if (jsendResponse.isSuccess()) {
                        // We have successfully logged location to the server
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {

                }
            });
        }
    }

}
