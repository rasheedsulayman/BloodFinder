package com.r4sh33d.iblood.additionaldetails;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.UserData;

public interface AdditionalDetailsContract {

    interface Presenter  {
        void start();
        void saveAdditionalDetails(UserData userData);
    }

    interface View  extends BaseContract.view{
        void onAdditionalDetailsSavedSuccessfully(UserData user);
    }
}
