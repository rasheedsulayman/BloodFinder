package com.r4sh33d.iblood.base;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public abstract void showLoading(String message);

    public abstract void dismissLoading();

    public abstract void showLoading();

    public abstract void showLoading(@StringRes int resId);

    public abstract void showToolbar();

    public abstract void hideToolbar();

}
