package com.r4sh33d.iblood.bloodrequest;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.BloodSearchData;

public interface BloodRequestContract {

    interface Presenter {
        void start();

        void requestForBlood(BloodSearchData bloodSearchData);
    }

    interface View extends BaseContract.view {
    }
}
