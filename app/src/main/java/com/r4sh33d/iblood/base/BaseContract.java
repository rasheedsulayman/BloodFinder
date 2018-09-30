package com.r4sh33d.iblood.base;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public interface BaseContract {

    interface view {
        void dismissLoading();

        public AlertDialog showError(CharSequence message);

        public AlertDialog showSuccessDialog(String message, DialogInterface.OnClickListener
                positiveClickListener);

        void showLoading(String message);

        void showToast(String message);
    }

}
