package com.r4sh33d.iblood.models.states_cities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LGA {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("id")
    @Expose
    public Integer id;

    @Override
    public String toString() {
        return name;
    }
}
