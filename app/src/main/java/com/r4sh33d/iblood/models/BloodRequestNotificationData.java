package com.r4sh33d.iblood.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class BloodRequestNotificationData  implements Parcelable {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("blood_seeker_fb_id")
    @Expose
    public String bloodSeekerFbId;
    @SerializedName("blood_seeker_name")
    @Expose
    public String bloodSeekerName;
    @SerializedName("blood_posting_id")
    @Expose
    public String bloodPostingId;


    public long timeArrived; //milliseconds since epoch
    public long sentTime;


    public BloodRequestNotificationData(String type, String bloodSeekerFbId, String bloodSeekerName,
                                        String bloodPostingId) {
        this.type = type;
        this.bloodSeekerFbId = bloodSeekerFbId;
        this.bloodSeekerName = bloodSeekerName;
        this.bloodPostingId = bloodPostingId;
    }

    public BloodRequestNotificationData(String type, String bloodSeekerFbId, String bloodSeekerName,
                                        String bloodPostingId, long timeArrived, long sentTime) {
        this.type = type;
        this.bloodSeekerFbId = bloodSeekerFbId;
        this.bloodSeekerName = bloodSeekerName;
        this.bloodPostingId = bloodPostingId;
        this.timeArrived = timeArrived;
        this.sentTime = sentTime;
    }

    public BloodRequestNotificationData (Map<String, String> remoteNotificationDataMap) {
        type = remoteNotificationDataMap.get("type");
        bloodSeekerFbId = remoteNotificationDataMap.get("blood_seeker_fb_id");
        bloodSeekerName = remoteNotificationDataMap.get("blood_seeker_name");
        bloodPostingId = remoteNotificationDataMap.get("blood_posting_id");
    }

    protected BloodRequestNotificationData(Parcel in) {
        type = in.readString();
        bloodSeekerFbId = in.readString();
        bloodSeekerName = in.readString();
        bloodPostingId = in.readString();
        timeArrived = in.readLong();
        sentTime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(bloodSeekerFbId);
        dest.writeString(bloodSeekerName);
        dest.writeString(bloodPostingId);
        dest.writeLong(timeArrived);
        dest.writeLong(sentTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BloodRequestNotificationData> CREATOR = new Creator<BloodRequestNotificationData>() {
        @Override
        public BloodRequestNotificationData createFromParcel(Parcel in) {
            return new BloodRequestNotificationData(in);
        }

        @Override
        public BloodRequestNotificationData[] newArray(int size) {
            return new BloodRequestNotificationData[size];
        }
    };
}
