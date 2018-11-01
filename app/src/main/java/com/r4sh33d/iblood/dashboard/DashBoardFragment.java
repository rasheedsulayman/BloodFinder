package com.r4sh33d.iblood.dashboard;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.bloodrequest.BloodRequestFragment;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.upload_blood_availaibility.UploadBloodAvailabilityFragment;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.PrefsUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends BaseFragment implements DashboardContract.View {
    PrefsUtils prefsUtils;
    DashboardContract.Presenter presenter;

    public DashBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle("Dashboard");
        prefsUtils = Provider.providePrefManager(getContext());
        setDrawerIconToHome();
        presenter = new DashboardPresenter(this, Provider.provideDataRetrofitService(),prefsUtils);
        registerUserForNotifications();
    }

    public void registerUserForNotifications() {
        if (!prefsUtils.getBoolean(Constants.PREF_KEY_IS_NOTIFICATION_SUBSCRIBED, false)) {
            UserData userData = prefsUtils.getPrefAsObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS,
                    UserData.class);
            presenter.subScribeNotification(prefsUtils.getString(Constants.PREF_KEY_NOTIFICATION_TOKEN, ""),
                    userData);
        }
    }

    @OnClick(R.id.find_donors_button)
    public void onClickFindDonorsButton() {
        replaceFragment(new BloodRequestFragment());
    }

    @OnClick(R.id.upload_blood_availability_button)
    public void onClickUploadBloodAvailaibilityButton() {
        replaceFragment(new UploadBloodAvailabilityFragment());
    }

}
