package com.r4sh33d.iblood.additionaldetails;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.AdditionalUserDetailsRequest;
import com.r4sh33d.iblood.models.User;
import com.r4sh33d.iblood.models.UserAuthRequest;

public interface AdditionalDetailsContract {

    interface Presenter  {
        void start();
        void saveAdditionalDetails(AdditionalUserDetailsRequest additionalUserDetailsRequest);
    }

    interface View  extends BaseContract.view{
        void onAdditionalDetailsSavedSuccessfuly(User user);
    }
}
