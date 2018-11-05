package com.r4sh33d.iblood.requesthisotry;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.NotificationPayload;
import com.r4sh33d.iblood.models.UserData;

import java.util.ArrayList;

public interface RequestHistoryContract {

    interface Presenter {
        void start();

        void fetUserRequestHistory(UserData userData);

    }

    interface View extends BaseContract.view {

        void onRequestPostingHistorySuccessfullyFetched(ArrayList<BloodPostingData> dataArrayList);
    }
}
