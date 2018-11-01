package com.r4sh33d.iblood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class BloodRequestNotificationData extends BaseNotificationData {


    @SerializedName("blood_seeker_fb_id")
    @Expose
    public String bloodSeekerFbId;
    @SerializedName("blood_seeker_name")
    @Expose
    public String bloodSeekerName;
    @SerializedName("blood_seeker_uid")
    @Expose
    public String bloodSeekerUid;
    @SerializedName("blood_posting_id")
    @Expose
    public String bloodPostingId;

    public BloodRequestNotificationData (String type, String bloodSeekerFbId, String bloodSeekerName,
                                        String bloodSeekerUid, String bloodPostingId) {
        super(type);
        this.bloodSeekerFbId = bloodSeekerFbId;
        this.bloodSeekerName = bloodSeekerName;
        this.bloodSeekerUid = bloodSeekerUid;
        this.bloodPostingId = bloodPostingId;
    }

    public BloodRequestNotificationData (HashMap<String, String> remoteNotificationDataMap) {
        super(remoteNotificationDataMap.get("type"));
        bloodSeekerFbId = remoteNotificationDataMap.get("blood_seeker_fb_id");
        bloodSeekerName = remoteNotificationDataMap.get("blood_seeker_name");
        bloodSeekerUid = remoteNotificationDataMap.get("blood_seeker_uid");
        bloodPostingId = remoteNotificationDataMap.get("blood_posting_id");
    }
}
