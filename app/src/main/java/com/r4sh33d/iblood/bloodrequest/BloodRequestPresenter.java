package com.r4sh33d.iblood.bloodrequest;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.r4sh33d.iblood.location.LocationUtil;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodSearchData;
import com.r4sh33d.iblood.models.MiniLocation;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.Constants.BloodPostingStatus;
import com.r4sh33d.iblood.utils.Data;
import com.r4sh33d.iblood.utils.JsendResponse;
import com.r4sh33d.iblood.utils.PrefsUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class BloodRequestPresenter implements BloodRequestContract.Presenter {

    private BloodRequestContract.View view;
    private DataService dataService;
    private Context context;
    PrefsUtils prefsUtils;

    BloodRequestPresenter(BloodRequestContract.View view, DataService dataService,
                          Context context) {
        this.view = view;
        this.dataService = dataService;
        this.context = context;
        prefsUtils = Provider.providePrefManager(context);
    }

    @Override
    public void start() {
    }

    @Override
    public void requestForBlood(BloodSearchData bloodSearchData) {
        view.showLoading("Fetching donors, Please wait . . .");
        if (!prefsUtils.doesContain(Constants.PREF_KEY_LOCATION_OBJECT)) {
            LocationUtil.getLastKnownLocation(context, new LocationUtil.LocationRetrievedListener() {
                @Override
                public void onLocationRetrieved(Location location) {
                    bloodSearchData.seekersLocation = new MiniLocation(location.getLatitude(), location.getLongitude());
                }

                @Override
                public void onFailed() {
                    //Failed
                    searchForBloodDonors(bloodSearchData);
                }
            });
        } else {
            //We have the location info
            bloodSearchData.seekersLocation = prefsUtils.getPrefAsObject(Constants.PREF_KEY_LOCATION_OBJECT, MiniLocation.class);
            searchForBloodDonors(bloodSearchData);
        }
    }

    //currently, we are pulling all entries on the DB. Then we are performing all filtering locally on the device.
    //This is for the purpose of the presentation/pitch. If we are ever going live, we should move all filtering to the server.
    public void searchForBloodDonors(BloodSearchData bloodSearchData) {
        dataService.getBloodAvailability().enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    Type type = new TypeToken<HashMap<String, BloodPostingData>>() {
                    }.getType();
                    HashMap<String, BloodPostingData> bloodPostings = new Gson().fromJson(jsendResponse.getData(), type);
                    view.onDonorsPostingsFetched(processResultList(new ArrayList<>(bloodPostings.values()), bloodSearchData));
                    view.dismissLoading();
                } else {
                    view.dismissLoading();
                    //TODO handle failure
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                view.dismissLoading();
                view.showError(JsendResponse.ERROR_MESSAGE);
            }
        });
    }

    public ArrayList<BloodPostingData> processResultList(ArrayList<BloodPostingData> bloodPostingsList,
                                                         BloodSearchData bloodSearchData) {
        ArrayList<BloodPostingData> resultsList = new ArrayList<>();
        for (BloodPostingData bloodPostingData : bloodPostingsList) {
            for (String compatibilityType : Data.bloodTypeCompatibilityMapping.get(bloodSearchData.bloodType)) {
                if (bloodPostingData.donorsBloodType.equals(compatibilityType) &&
                        bloodPostingData.donationType.equals(bloodSearchData.donationType) &&
                        !bloodPostingData.status.equals(BloodPostingStatus.ACCEPTED)
                        ) {
                    if (!TextUtils.isEmpty(bloodSearchData.preferedRangeKm) &&
                            ((Double.parseDouble(bloodSearchData.preferedRangeKm) * 1000 /*converting it to meters*/) >
                                    bloodPostingData.donorsLocation.distanceTo(bloodSearchData.seekersLocation))) {
                        resultsList.add(bloodPostingData);
                    } else if (TextUtils.isEmpty(bloodSearchData.preferedRangeKm)) {
                        resultsList.add(bloodPostingData);
                    }
                    break;
                }
            }
        }

        Timber.d("The result list size is " + resultsList.size());
        //At this stage we've added all compatible blood types to the result list, now we need to sort based on location
        sortBasedLocation(resultsList, bloodSearchData);
        return resultsList;
    }

    public void sortBasedLocation(ArrayList<BloodPostingData> bloodPostingsList,
                                  BloodSearchData bloodSearchData) {
        Collections.sort(bloodPostingsList, (o1, o2) -> {
            //TODO comeback and replace this comparision with real life road distances
            float distanceToO1 = o1.donorsLocation.distanceTo(bloodSearchData.seekersLocation);
            float distanceToO2 = o2.donorsLocation.distanceTo(bloodSearchData.seekersLocation);
            return Float.compare(distanceToO1, distanceToO2);
        });
    }
}
