package com.r4sh33d.iblood.base;

import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

public interface BaseContract {

    interface view {

        void dismissProgressDialogLoading();

        public AlertDialog showError(CharSequence message);

        public AlertDialog showSuccessDialog(String message, DialogInterface.OnClickListener
                positiveClickListener);

        void showProgressDialogLoading(String message);

        void showToast(String message);

        public void hideKeyboard();

        public void showLoading(String message);

        public void dismissLoading();

        void showLoading();

        void showLoading(@StringRes int resId);

        void showToolbar();

        void hideToolbar();
    }
}
