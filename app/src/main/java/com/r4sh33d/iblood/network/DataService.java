package com.r4sh33d.iblood.network;

import com.google.gson.JsonElement;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.models.UserLocation;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface DataService {

    @POST("/accounts/verifications")
    Call<JsonElement> requestOTP(@Body Map<String, String> phoneNumberPayload);

    @PUT("/users/{userId}.json")
    Call<JsonElement> saveAdditionalUserDetail(@Body UserData userData,
                                               @Path("userId") String userID);

    @GET("/users/{userId}.json")
    Call<JsonElement> getAdditionalUserDetails (@Path("userId") String userID);

    @POST("/blood_availability.json")
    Call<JsonElement> saveBloodAvailability (@Body BloodPostingData bloodPostingData);

    @GET("/blood_availability.json")
    Call<JsonElement> getBloodAvailability ();

    @PUT("/users/{userId}/location.json")
    Call<JsonElement> updateUserLocation (@Path("userId") String userID , @Body UserLocation userLocation);

    @POST("/accounts/customers/sessions")
    Call<JsonElement> authenticateUser(@Body Map<String, String> body);

}