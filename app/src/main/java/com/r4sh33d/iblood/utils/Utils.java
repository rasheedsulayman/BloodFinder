package com.r4sh33d.iblood.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.r4sh33d.iblood.models.KeyNameLOVPair;

import java.util.ArrayList;

public class Utils {

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static ArrayList<KeyNameLOVPair> getUserTypesList(){
        ArrayList<KeyNameLOVPair> keyNameLOVPairs = new ArrayList<>();
        keyNameLOVPairs.add(new KeyNameLOVPair(" Select user type", "select_user_type"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" Individual user", "individual_user"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" Blood bank", "blood_bank"));
        return keyNameLOVPairs;
    }

    public static ArrayList<KeyNameLOVPair> getReligionList() {
        ArrayList<KeyNameLOVPair> keyNameLOVPairs = new ArrayList<>();
        keyNameLOVPairs.add(new KeyNameLOVPair(" Select Religion", "select_religion"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" Don't care", "do_not_care"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" Christianity", "christianity"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" Islam", "islam"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" Others", "others"));
        return keyNameLOVPairs;
    }

    public static ArrayList<KeyNameLOVPair> getBloodGroups(){
        ArrayList<KeyNameLOVPair> keyNameLOVPairs = new ArrayList<>();
        keyNameLOVPairs.add(new KeyNameLOVPair(" Select blood type", "select_blood_group"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" A+", "A+"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" A-", "A-"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" B+", "B+"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" B-", "B-"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" AB+", "AB+"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" AB-", "AB-"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" O+", "O+"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" O-", "O-"));
        return keyNameLOVPairs;
    }

    public static ArrayList<KeyNameLOVPair> getDonationTypes(){
        ArrayList<KeyNameLOVPair> keyNameLOVPairs = new ArrayList<>();
        keyNameLOVPairs.add(new KeyNameLOVPair(" Select donation type", "select_donation_type"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" Free", "free"));
        keyNameLOVPairs.add(new KeyNameLOVPair(" Paid", "paid"));
        return keyNameLOVPairs;
    }


    public static boolean isLocationPermissionEnabled(Context context){
       return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
