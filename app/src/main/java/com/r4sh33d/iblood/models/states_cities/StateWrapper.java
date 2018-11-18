package com.r4sh33d.iblood.models.states_cities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateWrapper {

    @SerializedName("state")
    @Expose
    public State state;

    @Override
    public String toString() {
        return state.name;
    }
}