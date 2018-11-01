package com.r4sh33d.iblood.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.r4sh33d.iblood.dashboard.DashboardActivity;
import com.r4sh33d.iblood.R;

public abstract class BaseFragment extends Fragment implements BaseContract.view {
    BaseActivity baseActivity;
    public ProgressDialog progressDialog;

    public void setToolbarTitle(String title) {
        ActionBar ab = baseActivity.getSupportActionBar();
        ab.setTitle(title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity = (BaseActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(baseActivity);
    }

    public AlertDialog showError(CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity)
                .setMessage(message)
                .setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    public AlertDialog showSuccessDialog(String message, DialogInterface.OnClickListener
            positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity)
                .setMessage(message)
                .setPositiveButton("Ok", positiveClickListener);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    @Override
    public void showProgressDialogLoading(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialogLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public void replaceFragment(Fragment fragment) {
        String TAG = fragment.getClass().getSimpleName();
        baseActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.view_container, fragment, TAG)
                .addToBackStack(TAG)
                .commitAllowingStateLoss();
    }

    public void setDrawerIconToBack(){
        if (baseActivity instanceof DashboardActivity) {
            ((DashboardActivity)baseActivity).setDrawerIconToBack();
        }
    }

    public void setDrawerIconToHome(){
        if (baseActivity instanceof DashboardActivity) {
            ((DashboardActivity)baseActivity).setDrawerIconToHome();
        }
    }

    @Override
    public void showLoading(String message) {
        baseActivity.showLoading(message);
    }

    @Override
    public void onDestroyView() {
        dismissProgressDialogLoading();
        progressDialog = null;
        super.onDestroyView();
    }

    @Override
    public void hideKeyboard() {
        // Check if no view has focus:
        View view = baseActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) baseActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        baseActivity.replaceFragment(fragment , addToBackStack);
    }

        @Override
    public void onDetach() {
        baseActivity = null;
        super.onDetach();
    }

    @Override
    public void showToolbar() {
        baseActivity.showToolbar();
    }

    @Override
    public void hideToolbar() {
        baseActivity.hideToolbar();
    }


    @Override
    public void dismissLoading() {
        baseActivity.dismissLoading();
    }

    @Override
    public void showLoading() {
        baseActivity.showLoading();
    }

    @Override
    public void showLoading(int resId) {
        baseActivity.showLoading(resId);
    }
}
