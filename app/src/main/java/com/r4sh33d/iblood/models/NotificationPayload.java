package com.r4sh33d.iblood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationPayload < T > {

    @SerializedName("data")
    @Expose
    public T data;
    @SerializedName("to")
    @Expose
    public String destinationUID;

    public NotificationPayload(T data, String destinationUID) {
        this.data = data;
        this.destinationUID = destinationUID;
    }
}
