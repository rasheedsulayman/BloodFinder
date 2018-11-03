package com.r4sh33d.iblood.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData implements Parcelable {

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
    @SerializedName("firebaseId")
    @Expose
    public String firebaseID;

    @SerializedName("user_location")
    @Expose
    public UserLocation userLocation;

    @SerializedName("notification_token")
    @Expose
    public String notificationToken;

    public UserData (boolean isBloodBank, String name, String email,
                    String phoneNumber, String address, String religion,
                    String firebaseID) {
        this.isBloodBank = isBloodBank;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.religion = religion;
        this.firebaseID = firebaseID;
    }

    public UserData() {

    }

    protected UserData(Parcel in) {
        isBloodBank = in.readByte() != 0;
        name = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        religion = in.readString();
        firebaseID = in.readString();
        userLocation = in.readParcelable(UserLocation.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isBloodBank ? 1 : 0));
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeString(address);
        dest.writeString(religion);
        dest.writeString(firebaseID);
        dest.writeParcelable(userLocation, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}

