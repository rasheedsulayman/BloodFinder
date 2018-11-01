package com.r4sh33d.iblood.postingsresultslist;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.BloodPostingData;

public interface BloodPostingResultListContract {

    interface Presenter  {
        void start();
        void sendNotification(BloodPostingData bloodPostingData);
    }

    interface View  extends BaseContract.view{
        void onNotificationSent(BloodPostingData bloodPostingData);
    }
}
