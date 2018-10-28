package com.r4sh33d.iblood.bloodrequest;

import android.content.Context;
import android.location.Location;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.location.LocationUtil;
import com.r4sh33d.iblood.models.BloodSearchData;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.utils.PrefsUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class BloodRequestPresenter implements BloodRequestContract.Presenter  {

    private BloodRequestContract.View view;
    private DataService dataService;
    private Context context;
    PrefsUtils prefsUtils;
    private static final String TAG = BloodRequestPresenter.class.getSimpleName();

    BloodRequestPresenter (BloodRequestContract.View view, DataService dataService,
                          Context context /*TODO: don't pass context like this*/ ) {
        this.view = view;
        this.dataService = dataService;
        this.context = context;
        prefsUtils = Provider.providePrefManager(context);
    }

    @Override
    public void start() {}

    @Override
    public void requestForBlood(BloodSearchData bloodSearchData) {
         if (!prefsUtils.doesContain(Constants.PREF_KEY_LOCATION_OBJECT)){
             LocationUtil.getLastKnownLocation(context, new LocationUtil.LocationRetrievedListener() {
                 @Override
                 public void onLocationRetrievedListener(Location location) {

                 }

                 @Override
                 public void onFailed() {

                 }
             });
         }
    }

    public void searchForBloodDonors(BloodSearchData bloodSearchData) {

    }

}
