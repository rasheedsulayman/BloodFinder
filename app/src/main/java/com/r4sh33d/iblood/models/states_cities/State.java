package com.r4sh33d.iblood.models.states_cities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class State {
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("locals")
    @Expose
    public ArrayList<LGA> LGAs;
}