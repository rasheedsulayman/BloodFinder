package com.r4sh33d.iblood.login;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.models.UserAuthRequest;

public interface LoginContract {

    interface Presenter  {
        void start();
        void login (UserAuthRequest userAuthRequest);
    }

    interface View  extends BaseContract.view{
        void onUserSuccessfullyLoggedIn (UserData user);
    }
}
