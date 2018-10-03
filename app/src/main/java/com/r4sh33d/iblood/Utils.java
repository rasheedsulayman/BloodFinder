package com.r4sh33d.iblood;

import android.text.TextUtils;

public class Utils {

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
