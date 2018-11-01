package com.r4sh33d.iblood.dashboard;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.models.UserAuthRequest;
import com.r4sh33d.iblood.models.UserData;

public interface DashboardContract {

    interface Presenter  {
        void start();
        void subScribeNotification ( String refreshedToken, UserData userdata);
    }

    interface View  extends BaseContract.view{

    }
}
