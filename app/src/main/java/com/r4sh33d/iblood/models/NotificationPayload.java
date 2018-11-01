package com.r4sh33d.iblood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationPayload < T extends BaseNotificationData > {

    @SerializedName("data")
    @Expose
    public T data;
    @SerializedName("to")
    @Expose
    public String destinationUID;

}
