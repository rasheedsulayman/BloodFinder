package com.r4sh33d.iblood.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User  implements Parcelable{
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

    protected User(Parcel in) {
        kind = in.readString();
        localId = in.readString();
        email = in.readString();
        displayName = in.readString();
        idToken = in.readString();
        byte tmpRegistered = in.readByte();
        registered = tmpRegistered == 0 ? null : tmpRegistered == 1;
        refreshToken = in.readString();
        expiresIn = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(localId);
        dest.writeString(email);
        dest.writeString(displayName);
        dest.writeString(idToken);
        dest.writeByte((byte) (registered == null ? 0 : registered ? 1 : 2));
        dest.writeString(refreshToken);
        dest.writeString(expiresIn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
