package com.r4sh33d.iblood.postingsresultslist;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.NotificationPayload;

public interface BloodPostingResultListContract {

    interface Presenter  {
        void start();
        void sendNotification(NotificationPayload notificationPayload);
    }

    interface View  extends BaseContract.view{
        void onNotificationSent(NotificationPayload notificationPayload);
    }
}
