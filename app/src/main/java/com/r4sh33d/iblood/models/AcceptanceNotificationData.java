package com.r4sh33d.iblood.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class AcceptanceNotificationData  implements Parcelable{

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("blood_donor_fb_id")
    @Expose
    public String bloodDonorFbId;
    @SerializedName("blood_donor_name")
    @Expose
    public String bloodDonorName;
    @SerializedName("blood_posting_id")
    @Expose
    public String bloodPostingId;

    public long timeArrived; //milliseconds since epoch
    public long sentTime;



    public AcceptanceNotificationData (Map<String, String> remoteNotificationDataMap) {
        type = remoteNotificationDataMap.get("type");
        bloodDonorFbId = remoteNotificationDataMap.get("blood_donor_fb_id");
        bloodDonorName = remoteNotificationDataMap.get("blood_donor_name");
        bloodPostingId = remoteNotificationDataMap.get("blood_posting_id");
    }

    public AcceptanceNotificationData(String type, String bloodDonorFbId, String bloodDonorName,
                                      String bloodPostingId, long timeArrived, long sentTime) {
        this.type = type;
        this.bloodDonorFbId = bloodDonorFbId;
        this.bloodDonorName = bloodDonorName;
        this.bloodPostingId = bloodPostingId;
        this.timeArrived = timeArrived;
        this.sentTime = sentTime;
    }

    public AcceptanceNotificationData(String type, String bloodDonorFbId,
                                      String bloodDonorName, String bloodPostingId) {
        this.type = type;
        this.bloodDonorFbId = bloodDonorFbId;
        this.bloodDonorName = bloodDonorName;
        this.bloodPostingId = bloodPostingId;
    }

    protected AcceptanceNotificationData(Parcel in) {
        type = in.readString();
        bloodDonorFbId = in.readString();
        bloodDonorName = in.readString();
        bloodPostingId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(bloodDonorFbId);
        dest.writeString(bloodDonorName);
        dest.writeString(bloodPostingId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AcceptanceNotificationData> CREATOR = new Creator<AcceptanceNotificationData>() {
        @Override
        public AcceptanceNotificationData createFromParcel(Parcel in) {
            return new AcceptanceNotificationData(in);
        }

        @Override
        public AcceptanceNotificationData[] newArray(int size) {
            return new AcceptanceNotificationData[size];
        }
    };
}
