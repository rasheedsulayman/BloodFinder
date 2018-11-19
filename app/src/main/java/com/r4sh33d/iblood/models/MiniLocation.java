package com.r4sh33d.iblood.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MiniLocation implements Parcelable{

    @SerializedName("longitude")
    @Expose
    public double longitude;
    @SerializedName("latitude")
    @Expose
    public double latitude;

    @SerializedName("address")
    @Expose
    public String descriptiveAddress;


    public MiniLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //TODO get real directions with road with this https://stackoverflow.com/a/18312349/6484427
    public  float distanceTo (MiniLocation toLocation) {
        Location locationA = new Location("point A");
        locationA.setLatitude(latitude);
        locationA.setLongitude(longitude);
        Location locationB = new Location("point B");
        locationB.setLatitude(toLocation.latitude);
        locationB.setLongitude(toLocation.longitude);
        return locationA.distanceTo(locationB);
    }

    protected MiniLocation(Parcel in) {
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MiniLocation> CREATOR = new Creator<MiniLocation>() {
        @Override
        public MiniLocation createFromParcel(Parcel in) {
            return new MiniLocation(in);
        }

        @Override
        public MiniLocation[] newArray(int size) {
            return new MiniLocation[size];
        }
    };


}
