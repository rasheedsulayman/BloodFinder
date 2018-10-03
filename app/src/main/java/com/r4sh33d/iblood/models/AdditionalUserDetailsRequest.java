package com.r4sh33d.iblood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdditionalUserDetailsRequest {

    @SerializedName("is_blood_bank")
    @Expose
    public boolean isBloodBank;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("phone_number")
    @Expose
    public String phoneNumber;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("religion")
    @Expose
    public String religion;
}
