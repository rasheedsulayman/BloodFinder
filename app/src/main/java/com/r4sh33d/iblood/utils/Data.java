package com.r4sh33d.iblood.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.models.states_cities.State;
import com.r4sh33d.iblood.models.states_cities.StateWrapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Data {
    //Painful to write ☹️☹️☹️☹️
    public static HashMap<String, String[]> bloodTypeCompatibilityMapping = new HashMap<>();
    private static ArrayList<StateWrapper> statesWithCities = null;

    static {
        bloodTypeCompatibilityMapping.put("AB+", new String[]{"O-", "O+", "B-", "B+", "A-", "A+", "AB-", "AB+"});
        bloodTypeCompatibilityMapping.put("AB-", new String[]{"O-", "B-", "A-", "AB-"});
        bloodTypeCompatibilityMapping.put("A+", new String[]{"O-", "O+", "A-", "A+"});
        bloodTypeCompatibilityMapping.put("A-", new String[]{"O-", "A-"});
        bloodTypeCompatibilityMapping.put("B+", new String[]{"O-", "O+", "B-", "B+"});
        bloodTypeCompatibilityMapping.put("B-", new String[]{"O-", "B-"});
        bloodTypeCompatibilityMapping.put("O+", new String[]{"O-", "O+"});
        bloodTypeCompatibilityMapping.put("O-", new String[]{"O-"});
    }

    public static ArrayList<StateWrapper> getStatesWithCitiesList(Context context) {
        if (statesWithCities == null) {
            InputStream raw = context.getResources().openRawResource(R.raw.states);
            Reader rd = new BufferedReader(new InputStreamReader(raw));
            Type type = new TypeToken<ArrayList<StateWrapper>>() {
            }.getType();
            statesWithCities = new Gson().fromJson(rd, type);
            //Add Hint for the  state spinner;
            StateWrapper stateWrapper = new StateWrapper();
            stateWrapper.state = new State();
            stateWrapper.state.name = "Please select state";
            statesWithCities.add(0, stateWrapper);
        }
        return statesWithCities;
    }
}
