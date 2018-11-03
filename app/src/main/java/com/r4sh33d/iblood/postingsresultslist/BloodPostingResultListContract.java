package com.r4sh33d.iblood.postingsresultslist;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.NotificationPayload;
import com.r4sh33d.iblood.models.UserData;

public interface BloodPostingResultListContract {

    interface Presenter {
        void start();

        void fetchMoreDetails(BloodPostingData bloodPostingData);

        void sendNotification(UserData bloodSeekerData, BloodPostingData bloodPostingData,
                              NotificationPayload<BloodRequestNotificationData> notificationPayload);
    }

    interface View extends BaseContract.view {

        void onDetailsSuccessfullyFetched(UserData bloodDonorData, BloodPostingData bloodPostingData);

        void onNotificationSent();
    }
}
