package com.r4sh33d.iblood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("kind")
    @Expose
    public String kind;
    @SerializedName("localId")
    @Expose
    public String localId;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("displayName")
    @Expose
    public String displayName;
    @SerializedName("idToken")
    @Expose
    public String idToken;
    @SerializedName("registered")
    @Expose
    public Boolean registered;
    @SerializedName("refreshToken")
    @Expose
    public String refreshToken;
    @SerializedName("expiresIn")
    @Expose
    public String expiresIn;
}
