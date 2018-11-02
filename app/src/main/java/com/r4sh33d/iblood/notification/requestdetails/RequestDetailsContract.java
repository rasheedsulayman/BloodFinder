package com.r4sh33d.iblood.notification.requestdetails;

import android.app.AlertDialog;
import android.support.v4.app.DialogFragment;

import com.r4sh33d.iblood.base.BaseContract;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.UserData;

public interface RequestDetailsContract {

    interface Presenter {

        void fetchDetails(BloodRequestNotificationData bloodRequestNotificationData);
    }

    interface View {

        void showLoading(String message);

        void dismissLoading();

        void showError(String message);

        void showAlertDialog(String message);

        void onDetailsSuccessfullyFetched(UserData user, BloodPostingData bloodPostingData);

        void onBloodPostingSuccessfullyFetched();
    }
}
