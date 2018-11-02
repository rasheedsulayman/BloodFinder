package com.r4sh33d.iblood.upload_blood_availaibility;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.BloodPostingData;

public interface UploadBloodAvailabilityContract {

    interface Presenter  {
        void start();
        void uploadBloodTypeAvailability(BloodPostingData bloodPostingData);
    }

    interface View  extends BaseContract.view{
        void onBloodTypeAvailabilityUploaded();
    }
}
