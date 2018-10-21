package com.r4sh33d.iblood.upload_blood_availaibility;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.BloodSearchData;

public interface UploadBloodAvailabilityContract {

    interface Presenter  {
        void start();
        void uploadBloodTypeAvailability(BloodSearchData bloodSearchData);
    }

    interface View  extends BaseContract.view{
        void onBloodTypeAvailabilityUploaded(BloodSearchData bloodSearchData);
    }
}
