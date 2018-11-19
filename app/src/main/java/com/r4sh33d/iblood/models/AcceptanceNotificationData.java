package com.r4sh33d.iblood.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class AcceptanceNotificationData implements Parcelable {

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

    @SerializedName("donor_preferred_donation_center_name")
    @Expose
    public String donorPreferredDonationCenterName;

    @SerializedName("donor_preferred_donation_center_lat")
    @Expose
    public String donorPreferredDonationCenterLat;

    @SerializedName("donor_preferred_donation_center_long")
    @Expose
    public String donorPreferredDonationCenterLong;

    @SerializedName("donor_preferred_donation_center_google_map_name")
    @Expose
    public String donorPreferredDonationCenterGoogleMapName;

    @SerializedName("schedule_time_millis")
    @Expose
    public String scheduleTimeMillis; //milliseconds since epoch

    public long timeArrived; //milliseconds since epoch
    public long sentTime;


    public AcceptanceNotificationData(Map<String, String> remoteNotificationDataMap) {
        type = remoteNotificationDataMap.get("type");
        bloodDonorFbId = remoteNotificationDataMap.get("blood_donor_fb_id");
        bloodDonorName = remoteNotificationDataMap.get("blood_donor_name");
        bloodPostingId = remoteNotificationDataMap.get("blood_posting_id");
        scheduleTimeMillis = remoteNotificationDataMap.get("schedule_time_millis");
        donorPreferredDonationCenterName = remoteNotificationDataMap.get("donor_preferred_donation_center_name");
        donorPreferredDonationCenterLat = remoteNotificationDataMap.get("donor_preferred_donation_center_lat");
        donorPreferredDonationCenterLong = remoteNotificationDataMap.get("donor_preferred_donation_center_long");
        donorPreferredDonationCenterGoogleMapName = remoteNotificationDataMap.get("donor_preferred_donation_center_google_map_name");
    }

    public AcceptanceNotificationData(String type, String bloodDonorFbId, String bloodDonorName,
                                      String bloodPostingId, String donorPreferredDonationCenterName,
                                      String donorPreferredDonationCenterLat, String donorPreferredDonationCenterLong,
                                      String donorPreferredDonationCenterGoogleMapName, String scheduleTimeMillis) {
        this.type = type;
        this.bloodDonorFbId = bloodDonorFbId;
        this.bloodDonorName = bloodDonorName;
        this.bloodPostingId = bloodPostingId;
        this.donorPreferredDonationCenterName = donorPreferredDonationCenterName;
        this.donorPreferredDonationCenterLat = donorPreferredDonationCenterLat;
        this.donorPreferredDonationCenterLong = donorPreferredDonationCenterLong;
        this.donorPreferredDonationCenterGoogleMapName = donorPreferredDonationCenterGoogleMapName;
        this.scheduleTimeMillis = scheduleTimeMillis;
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
        donorPreferredDonationCenterName = in.readString();
        donorPreferredDonationCenterLat = in.readString();
        donorPreferredDonationCenterLong = in.readString();
        donorPreferredDonationCenterGoogleMapName = in.readString();
        scheduleTimeMillis = in.readString();
        timeArrived = in.readLong();
        sentTime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(bloodDonorFbId);
        dest.writeString(bloodDonorName);
        dest.writeString(bloodPostingId);
        dest.writeString(donorPreferredDonationCenterName);
        dest.writeString(donorPreferredDonationCenterLat);
        dest.writeString(donorPreferredDonationCenterLong);
        dest.writeString(donorPreferredDonationCenterGoogleMapName);
        dest.writeString(scheduleTimeMillis);
        dest.writeLong(timeArrived);
        dest.writeLong(sentTime);
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
