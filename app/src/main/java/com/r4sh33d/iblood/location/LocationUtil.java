package com.r4sh33d.iblood.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.r4sh33d.iblood.utils.Utils;

import timber.log.Timber;

public class LocationUtil {

    @SuppressLint("MissingPermission")
    public static void getLastKnownLocation(Context context, LocationRetrievedListener locationRetrievedListener) {
        if (!Utils.isLocationPermissionEnabled(context)) {
            locationRetrievedListener.onFailed();
            return;
        }
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        locationRetrievedListener.onLocationRetrievedListener(location);
                        Timber.d("Got last know location: %s", location);
                    }
                }).addOnFailureListener(e -> {
            //We failed
            locationRetrievedListener.onFailed();
            Timber.e(e, "Failed to get location:");
        });

    }

    public interface LocationRetrievedListener {
        void onLocationRetrievedListener(Location location);
        void onFailed();
    }
}
