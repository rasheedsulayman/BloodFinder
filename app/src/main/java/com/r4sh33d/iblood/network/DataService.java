package com.r4sh33d.iblood.network;

import com.google.gson.JsonElement;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.models.UserLocation;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface DataService {

    @PUT("/users/{userId}.json")
    Call<JsonElement> saveAdditionalUserDetail(@Body UserData userData,
                                               @Path("userId") String userID);

    @GET("/users/{userId}.json")
    Call<JsonElement> getAdditionalUserDetails(@Path("userId") String userID);

    @GET("/blood_availability/{bloodPostingId}.json")
    Call<JsonElement> getBloodPosting(@Path("bloodPostingId") String userID);

    @POST("/blood_availability.json")
    Call<JsonElement> saveBloodAvailability(@Body BloodPostingData bloodPostingData);

    @PATCH("/blood_availability/{bloodPostingId}.json")
    Call<JsonElement> updateBloodPosting(@Path("bloodPostingId") String bloodPostingID, @Body HashMap<String, String> payload);

    @GET("/blood_availability.json")
    Call<JsonElement> getBloodAvailability();

    @PUT("/users/{userId}/location.json")
    Call<JsonElement> updateUserLocation(@Path("userId") String userID, @Body UserLocation userLocation);

    @PUT("/users/{userId}/notification_token.json")
    Call<String> updateUserNotificationToken(@Path("userId") String userID, @Body String refreshedToken);

    @PUT("/blood_availability/{bloodPostingId}/bloodPosting_id.json")
    Call<String> uploadBloodPostingId(@Path("bloodPostingId") String bloodPostingId, @Body String bloodPostingIdBody);

    @PUT("/blood_request_history/{userId}/{bloodPostingId}.json")
    Call<JsonElement> updateUserRequestHistory(@Path("bloodPostingId") String bloodPostingId, @Path("userId") String userID ,
                                      @Body BloodPostingData bloodPostingData);
}