package com.r4sh33d.iblood;

import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
        replaceFragment(new LoginFragment() , false);
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
        ViewUtils.show(progressBarRoot,progressBar, progressMessage);
        progressMessage.setText(message);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

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
