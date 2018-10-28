package com.r4sh33d.iblood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BloodSearchData {

    @SerializedName("donor_blood_type")
    @Expose
    public String donorsBloodType;
    @SerializedName("donation_type")
    @Expose
    public String donationType;
    @SerializedName("donor_email")
    @Expose
    public String donorsEmail;
    @SerializedName("donor_name")
    @Expose
    public String donorsName;
    @SerializedName("donor_phone_number")
    @Expose
    public String donorsPhoneNumber;
    @SerializedName("donor_firebase_id")
    @Expose
    public String donorsFirebaseId;
    @SerializedName("donor_religion")
    @Expose
    public String donorsReligion;
    @SerializedName("donor_location")
    @Expose
    public UserLocation donorsLocation;

    public BloodSearchData(String donorsBloodType, String donationType, String donorsEmail,
                           String donorsName, String donorsPhoneNumber, String donorsFirebaseId,
                           String donorsReligion, UserLocation donorsLocation) {
        this.donorsBloodType = donorsBloodType;
        this.donationType = donationType;
        this.donorsEmail = donorsEmail;
        this.donorsName = donorsName;
        this.donorsPhoneNumber = donorsPhoneNumber;
        this.donorsFirebaseId = donorsFirebaseId;
        this.donorsReligion = donorsReligion;
        this.donorsLocation = donorsLocation;
    }
}