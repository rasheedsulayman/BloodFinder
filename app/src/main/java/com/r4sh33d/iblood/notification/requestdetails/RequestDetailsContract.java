package com.r4sh33d.iblood.notification.requestdetails;

import com.r4sh33d.iblood.models.AcceptanceNotificationData;
import com.r4sh33d.iblood.models.BloodDonationCenter;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.NotificationPayload;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.models.MiniLocation;

import java.util.ArrayList;

public interface RequestDetailsContract {

    interface Presenter {

        void fetchNearbyBloodDonationCenter(MiniLocation bloodSeekerLocation, MiniLocation bloodBankLocation);

        void fetchDetails(BloodRequestNotificationData bloodRequestNotificationData);

        void sendNotification(UserData bloodSeekerData, NotificationPayload<AcceptanceNotificationData> notificationPayload);
    }

    interface View {

        void showLoading(String message);

        void dismissLoading();

        void showError(String message);

        void onDetailsSuccessfullyFetched(UserData bloodSeekerData, BloodPostingData bloodPostingData);

        void onNotificationSuccessfullySent();

        void onNearbyBloodBanksFetched(ArrayList <BloodDonationCenter> donationCenters);
    }
}
