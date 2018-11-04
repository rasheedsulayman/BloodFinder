package com.r4sh33d.iblood.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BloodPostingData implements Parcelable {

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

    @SerializedName("bloodPosting_id")
    @Expose
    public String id;

    @SerializedName("acceptance_status")
    @Expose
    public String status;

    @SerializedName("accepted_seeker_id")
    @Expose
    public String acceptedSeekerId;

    public BloodPostingData(String donorsBloodType, String donationType, String donorsEmail,
                            String donorsName, String donorsPhoneNumber, String donorsFirebaseId,
                            String donorsReligion, UserLocation donorsLocation , String id) {
        this.donorsBloodType = donorsBloodType;
        this.donationType = donationType;
        this.donorsEmail = donorsEmail;
        this.donorsName = donorsName;
        this.donorsPhoneNumber = donorsPhoneNumber;
        this.donorsFirebaseId = donorsFirebaseId;
        this.donorsReligion = donorsReligion;
        this.donorsLocation = donorsLocation;
        this.id = id;
    }

    public BloodPostingData(String donorsBloodType, String donationType, String donorsEmail,
                            String donorsName, String donorsPhoneNumber, String donorsFirebaseId,
                            String donorsReligion, UserLocation donorsLocation, String id, String status) {
        this(donorsBloodType, donationType, donorsEmail, donorsName, donorsPhoneNumber, donorsFirebaseId,
                donorsReligion, donorsLocation, id);
        this.status = status;
    }

    protected BloodPostingData(Parcel in) {
        donorsBloodType = in.readString();
        donationType = in.readString();
        donorsEmail = in.readString();
        donorsName = in.readString();
        donorsPhoneNumber = in.readString();
        donorsFirebaseId = in.readString();
        donorsReligion = in.readString();
        donorsLocation = in.readParcelable(UserLocation.class.getClassLoader());
        id = in.readString();
        status = in.readString();
        acceptedSeekerId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(donorsBloodType);
        dest.writeString(donationType);
        dest.writeString(donorsEmail);
        dest.writeString(donorsName);
        dest.writeString(donorsPhoneNumber);
        dest.writeString(donorsFirebaseId);
        dest.writeString(donorsReligion);
        dest.writeParcelable(donorsLocation, flags);
        dest.writeString(id);
        dest.writeString(status);
        dest.writeString(acceptedSeekerId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BloodPostingData> CREATOR = new Creator<BloodPostingData>() {
        @Override
        public BloodPostingData createFromParcel(Parcel in) {
            return new BloodPostingData(in);
        }

        @Override
        public BloodPostingData[] newArray(int size) {
            return new BloodPostingData[size];
        }
    };
}