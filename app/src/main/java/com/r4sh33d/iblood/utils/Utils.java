package com.r4sh33d.iblood.utils;

import android.text.TextUtils;

import com.r4sh33d.iblood.models.UserType;

import java.util.ArrayList;

public class Utils {

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static ArrayList<UserType> getUserTypesList(){
        ArrayList<UserType> userTypes = new ArrayList<>();
        userTypes.add(new UserType("Select user type", "select_user_type"));
        userTypes.add(new UserType("Individual user", "individual_user"));
        userTypes.add(new UserType("Blood bank", "blood_bank"));
        return userTypes;
    }
}
