package com.r4sh33d.iblood.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.r4sh33d.iblood.donationHistory.DonationHistoryFragment;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.base.BaseActivity;
import com.r4sh33d.iblood.location.LocationUpdateService;
import com.r4sh33d.iblood.login.LoginActivity;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.requesthisotry.RequestHistoryFragment;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.PrefsUtils;
import com.r4sh33d.iblood.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String USER_KEY = "UserIntentKey";
    private static final String TAG = DashboardActivity.class.getSimpleName();

    @BindView(R.id.progress_bar_root)
    ConstraintLayout progressBarRoot;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.progress_message)
    TextView progressMessage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    PrefsUtils prefsUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        startLocationService();
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        prefsUtils = Provider.providePrefManager(this);
        UserData userData = prefsUtils.getPrefAsObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS,
                UserData.class);
        if (userData != null) {
            // Name, email address, and profile photo Url
            setNavigationViewHeaderDetails(userData);
        }
        //coming from the launcher
        replaceFragment(new DashBoardFragment(), false);
        drawerToggle.setToolbarNavigationClickListener(v -> onBackPressed());
    }

    void startLocationService() {
        Intent intent = new Intent(this, LocationUpdateService.class);
        intent.setAction(LocationUpdateService.GET_LAST_KNOWN_LOCATION);
        startService(intent);
    }

    void setNavigationViewHeaderDetails(UserData user) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView userProfilePic = headerView.findViewById(R.id.user_profile_pic);
        TextView userDisplayName = headerView.findViewById(R.id.user_display_name);
        TextView userEmail = headerView.findViewById(R.id.user_email);
        userDisplayName.setText(String.format("%s %s", user.firstName, user.lastName));
        userEmail.setText(user.phoneNumber);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_acitivity, menu);
        return true;
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


    public void setDrawerIconToBack() {
        drawerToggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerToggle.syncState();
        lockDrawer();
    }

    public void lockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void unlockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void setDrawerIconToHome() {
        drawerToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        drawerToggle.syncState();
        unlockDrawer();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_dashboard:
                replaceFragment(new DashBoardFragment(), false);
                break;
            case R.id.nav_donation_history:
                replaceFragment(new DonationHistoryFragment(), false);
                break;
            case R.id.nav_request_history:
                replaceFragment(new RequestHistoryFragment(), false);
                break;
            case R.id.nav_logout:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            //TODO add notification/messages fragment for users to come back and perform actions they perform when they have active notifications
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showLoading(String message) {
        drawerLayout.setFitsSystemWindows(false);
        navigationView.setFitsSystemWindows(false);
        ViewUtils.show(progressBarRoot, progressBar, progressMessage);
        progressMessage.setText(message);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void dismissLoading() {
        drawerLayout.setFitsSystemWindows(true);
        navigationView.setFitsSystemWindows(true);
        ViewUtils.hide(progressBarRoot);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void showLoading() {
        showLoading("Please wait");
    }

    @Override
    public void showLoading(int resId) {
        showLoading(getResources().getString(resId));
    }

    @Override
    public void showToolbar() {
    }

    @Override
    public void hideToolbar() {
    }

}
