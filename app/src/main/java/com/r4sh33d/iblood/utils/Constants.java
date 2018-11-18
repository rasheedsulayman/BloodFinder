package com.r4sh33d.iblood.utils;

public interface Constants {

    String phoneNumberSuffix = "@iblood-7253a.firebaseio.com";
    String PREF_KEY_ADDITIONAL_USER_DETAILS = "additional_user_details";
    String PREF_KEY_LOCATION_OBJECT = "location_object";
    String PREF_KEY_IS_NOTIFICATION_SUBSCRIBED = "is_notification_subscribed";
    String PREF_KEY_NOTIFICATION_LIST = "notification_list";
    String PREF_KEY_NOTIFICATION_TOKEN = "notification_token";
    String PACKAGE_NAME = "com.r4sh33d.iblood.location";


    interface BloodPostingStatus {
        String ACCEPTED = "accepted";
        String PENDING = "pending";
    }
}
