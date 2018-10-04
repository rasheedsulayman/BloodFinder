package com.r4sh33d.iblood.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserType {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("key")
    @Expose
    public String key;

    public UserType(String name, String key) {
        this.name = name;
        this.key = key;
    }
}
