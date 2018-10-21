package com.r4sh33d.iblood.upload_blood_availaibility;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.r4sh33d.iblood.emailregistration.EmailRegistrationContract;
import com.r4sh33d.iblood.models.BloodSearchData;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.models.UserAuthRequest;
import com.r4sh33d.iblood.network.AuthService;
import com.r4sh33d.iblood.network.DataService;
import com.r4sh33d.iblood.utils.JsendResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UploadBloodAvailabilityPresenter implements UploadBloodAvailabilityContract.Presenter {

    private UploadBloodAvailabilityContract.View view;
    private DataService dataService;
    private static final String TAG = UploadBloodAvailabilityPresenter.class.getSimpleName();

    UploadBloodAvailabilityPresenter(UploadBloodAvailabilityContract.View view, DataService dataService ) {
        this.view = view;
        this.dataService = dataService;
    }

    @Override
    public void start() {}

    @Override
    public void uploadBloodTypeAvailability(BloodSearchData bloodSearchData) {
        view.showLoading();
        dataService.saveBloodAvailability(bloodSearchData).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                view.dismissLoading();
                JsendResponse jsendResponse = new JsendResponse(response.body(), response.errorBody());
                if (jsendResponse.isSuccess()) {
                    BloodSearchData data = new Gson().fromJson(jsendResponse.getData(), BloodSearchData.class);
                    view.onBloodTypeAvailabilityUploaded(bloodSearchData);
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
