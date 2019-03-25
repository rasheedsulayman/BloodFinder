package com.r4sh33d.iblood.donationHistory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.utils.JsendResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationHistoryPresenter implements DonationHistoryContract.Presenter {
    DonationHistoryContract.View view;
    private DataService dataService;

    public DonationHistoryPresenter(DonationHistoryContract.View view, DataService dataService) {
        this.view = view;
        this.dataService = dataService;
    }


    @Override
    public void start() {

    }

    @Override
    public void fetchUserDonationHistory(UserData userData) {
        view.showLoading("Loading your donation history, Please wait. . .");
        dataService.getBloodDonationHistory("\"donor_firebase_id\"", String.format("\"%s\"", userData.firebaseID))
                .enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                view.dismissLoading();
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    Type type = new TypeToken<HashMap<String, BloodPostingData>>() {
                    }.getType();
                    HashMap<String, BloodPostingData> bloodPostings = new Gson().fromJson(jsendResponse.getData(), type);
                    view.onDonationHistorySuccessfullyFetched(new ArrayList<>(bloodPostings.values()));

                } else {
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
}
