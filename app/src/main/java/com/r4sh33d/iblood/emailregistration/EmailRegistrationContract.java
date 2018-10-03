package com.r4sh33d.iblood.emailregistration;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.models.UserAuthRequest;

public interface EmailRegistrationContract {

    interface Presenter  {
        void start();
        void registerUserEmail(UserAuthRequest userAuthRequest);
    }

    interface View  extends BaseContract.view{
        void onUserEmailRegistered(User user);
    }
}
