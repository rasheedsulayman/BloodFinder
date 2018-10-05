package com.r4sh33d.iblood.network;

import com.google.gson.JsonElement;
import com.r4sh33d.iblood.models.UserData;

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

    @POST("/accounts/customers/sessions")
    Call<JsonElement> authenticateUser(@Body Map<String, String> body);

}