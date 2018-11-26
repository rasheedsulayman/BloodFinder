package com.r4sh33d.iblood.DonationHistory;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.UserData;

import java.util.ArrayList;

public interface DonationHistoryContract {

    interface Presenter {
        void start();

        void fetchUserDonationHistory(UserData userData);

    }

    interface View extends BaseContract.view {

        void onDonationHistorySuccessfullyFetched(ArrayList<BloodPostingData> dataArrayList);
    }
}
