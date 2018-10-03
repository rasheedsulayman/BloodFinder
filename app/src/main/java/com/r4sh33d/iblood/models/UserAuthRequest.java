package com.r4sh33d.iblood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAuthRequest {

    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("returnSecureToken")
    @Expose
    public Boolean returnSecureToken;

    public UserAuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
        this.returnSecureToken = true;
    }
}
