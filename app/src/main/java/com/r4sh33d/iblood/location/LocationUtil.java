package com.r4sh33d.iblood.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.models.UserLocation;
import com.r4sh33d.iblood.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class LocationUtil {

    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;


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
                        locationRetrievedListener.onLocationRetrieved(location);
                        Timber.d("Got last know location: %s", location);
                    }
                }).addOnFailureListener(e -> {
            //We failed
            locationRetrievedListener.onFailed();
            Timber.e(e, "Failed to get location:");
        });

    }


    public static void  getAddressFromLatLongAsync (Location location, Context context ,
                                                    LocationAddressRetrievedListener listener){
        new AsyncTask<Void, Void , String>(){

            @Override
            protected String doInBackground(Void... voids) {
                 return getAddressFromLatLong(location , context);
            }

            @Override
            protected void onPostExecute(String result) {
                listener.onLocationAddressRetried(result);
            }
        };

    }


    public static  String getAddressFromLatLong (Location location, Context context) {
        if (context == null){
            return "";
        }
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String resultAddress = "";
        String errorMessage = "";

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = "Service not availaible";
            Timber.e(errorMessage);
            Timber.e(ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = context.getString(R.string.invalid_lat_long_used);
            Timber.e(errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude());
            Timber.e(illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "No address found";
                Timber.e(errorMessage);
            }
            //deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);

        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<>();
            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            Timber.i("Address  found");
            resultAddress = TextUtils.join(System.getProperty("line.separator"), addressFragments);
            //deliverResultToReceiver(Constants.SUCCESS_RESULT,

        }
        return resultAddress;
    }

    public interface LocationAddressRetrievedListener {
        void onLocationAddressRetried(String locationAdress);
    }
    public interface LocationRetrievedListener {
        void onLocationRetrieved(Location location);

        void onFailed();
    }
}
