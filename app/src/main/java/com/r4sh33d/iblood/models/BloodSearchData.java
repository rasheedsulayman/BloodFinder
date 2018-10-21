package com.r4sh33d.iblood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BloodSearchData {

    @SerializedName("blood_type")
    @Expose
    public String bloodType;
    @SerializedName("donation_type")
    @Expose
    public String donationType;
    @SerializedName("donors_email")
    @Expose
    public String donorsEmail;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("phone_number")
    @Expose
    public String phoneNumber;
    @SerializedName("donors_firebase_id")
    @Expose
    public String donorsFirebaseId;
    @SerializedName("donors_religion")
    @Expose
    public String donorsReligion;
    @SerializedName("donors_location")
    @Expose
    public String donorsLocation;
    @SerializedName("donors_longlat")
    @Expose
    public String donorsLongLat;

    public BloodSearchData(String bloodType, String donationType, String donorsEmail, String name,
                           String phoneNumber, String donorsFirebaseId, String donorsReligion,
                           String donorsLocation, String donorsLongLat) {
        this.bloodType = bloodType;
        this.donationType = donationType;
        this.donorsEmail = donorsEmail;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.donorsFirebaseId = donorsFirebaseId;
        this.donorsReligion = donorsReligion;
        this.donorsLocation = donorsLocation;
        this.donorsLongLat = donorsLongLat;
    }
}