package com.r4sh33d.iblood.login;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.base.BaseActivity;
import com.r4sh33d.iblood.login.LoginFragment;
import com.r4sh33d.iblood.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progress_bar_root)
    ConstraintLayout progressBarRoot;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.progress_message)
    TextView progressMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        hideToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            replaceFragment(new LoginFragment(), false);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    @Override
    public void showLoading() {
        showLoading("Please wait");
    }

    @Override
    public void showLoading(int resId) {
        showLoading(getString(resId));
    }

    @Override
    public void showLoading(String message) {
        ViewUtils.show(progressBarRoot, progressBar, progressMessage);
        progressMessage.setText(message);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //We've been granted the required permission
            replaceFragment(new LoginFragment(), false);
        }else {
            showDialog("We need the Location permission the to get your location information. The matching " +
                            "process is based on the location information retrieved from your device"
                    , (dialog, which) -> finish());
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void dismissLoading() {
        ViewUtils.hide(progressBarRoot);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void hideToolbar() {
        getSupportActionBar().hide();
    }

    public void showToolbar() {
        getSupportActionBar().show();
    }

}

