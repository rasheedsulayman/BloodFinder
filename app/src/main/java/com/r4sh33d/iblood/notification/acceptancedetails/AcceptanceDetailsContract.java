package com.r4sh33d.iblood.notification.acceptancedetails;

import com.r4sh33d.iblood.models.AcceptanceNotificationData;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.NotificationPayload;
import com.r4sh33d.iblood.models.UserData;

public interface AcceptanceDetailsContract {

    interface Presenter {

        void fetchDetails(AcceptanceNotificationData notificationData);

    }

    interface View {

        void showLoading(String message);

        void dismissLoading();

        void showError(String message);

        void onDetailsSuccessfullyFetched(UserData bloodDonorData);

    }
}
