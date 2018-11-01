package com.r4sh33d.iblood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseNotificationData {

    public static final String BLOOD_REQUEST_NOTIFICATION_TYPE = "blood_request_type";
    public static final String ACCEPTANCE_NOTIFICATION_TYPE = "acceptance_type";


    @SerializedName("type")
    @Expose
    public String type;

    public BaseNotificationData(String type) {
        this.type = type;
    }
}
