package com.r4sh33d.iblood.network;

import com.google.gson.JsonElement;
import com.r4sh33d.iblood.models.AdditionalUserDetailsRequest;
import com.r4sh33d.iblood.models.UserAuthRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface AccountService {

    @POST("/accounts/verifications")
    Call<JsonElement> requestOTP(@Body Map<String, String> phoneNumberPayload);


    @PUT("/accounts/{identifier}/verifications")
    Call<JsonElement> activateDevice(@Path("identifier") String phoneNumber,
                                     @Body Map<String, String> body);

    @POST("/signupNewUser")
    Call<JsonElement> registerUserEmail(@Body UserAuthRequest payload);


    @PUT("/users/{userId}.json")
    Call<JsonElement> saveAdditionalUserDetail(@Body AdditionalUserDetailsRequest additionalUserDetailsRequest,
                                               @Path("userId") String userID);

    @GET("/users/{userId}.json")
    Call<JsonElement> getAdditionalUserDetails (@Path("userId") String userID);

    @POST("/accounts/customers/sessions")
    Call<JsonElement> authenticateUser(@Body Map<String, String> body);

}