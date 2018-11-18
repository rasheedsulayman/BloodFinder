package com.r4sh33d.iblood.postingsresultslist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.BloodRequestNotificationData;
import com.r4sh33d.iblood.models.NotificationPayload;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.PrefsUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.r4sh33d.iblood.notification.services.NotificationHandlerService.BLOOD_REQUEST_NOTIFICATION_TYPE;

/**
 * A simple {@link Fragment} subclass.
 */

public class BloodPostingResultListFragment extends BaseFragment implements
        PostingsListAdapter.OnBloodPostingItemClickListener, BloodPostingResultListContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private static final String KEY_BLOOD_POSTING_DATA = "bloodposting";
    BloodPostingResultListContract.Presenter presenter;
    PrefsUtils prefsUtils;
    UserData bloodSeekerData;

    public static BloodPostingResultListFragment newInstance(ArrayList<BloodPostingData> resultslist) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_BLOOD_POSTING_DATA, resultslist);
        BloodPostingResultListFragment fragment = new BloodPostingResultListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public BloodPostingResultListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blood_posting_search_result_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle("Donor's List");
        prefsUtils = Provider.providePrefManager(getContext());
        bloodSeekerData = prefsUtils.getPrefAsObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS, UserData.class);
        presenter = new BloodPostingResultListPresenter(this, Provider.provideNotificationRetrofitService(),
                Provider.provideDataRetrofitService());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<BloodPostingData> resultsList = getArguments().getParcelableArrayList(KEY_BLOOD_POSTING_DATA);
        recyclerView.setAdapter(new PostingsListAdapter(this, resultsList));
    }

    @Override
    public void onBloodPostingItemClicked(BloodPostingData bloodPostingData) {
        presenter.fetchMoreDetails(bloodPostingData);
    }

    @Override
    public void onDetailsSuccessfullyFetched(UserData bloodDonorData, BloodPostingData bloodPostingData) {
        new MaterialDialog.Builder(getContext())
                .title("Send Request?")
                .content("A blood donation request will be sent to " + bloodPostingData.donorsName)
                .positiveColor(getResources().getColor(R.color.blood_red))
                .negativeColor(getResources().getColor(R.color.blood_red))
                .positiveText("Send")
                .negativeText("Cancel")
                .onPositive((dialog, which) -> {
                    BloodRequestNotificationData notificationData =
                            new BloodRequestNotificationData(
                                    BLOOD_REQUEST_NOTIFICATION_TYPE,
                                    bloodSeekerData.firebaseID,
                                    String.format("%s %s", bloodSeekerData.firstName, bloodSeekerData.lastName),
                                    bloodPostingData.id
                            );
                    NotificationPayload<BloodRequestNotificationData> notificationPayload
                            = new NotificationPayload<>(notificationData, bloodDonorData.notificationToken);
                    presenter.sendNotification(bloodSeekerData, bloodPostingData, notificationPayload);
                })
                .onNegative((dialog, which) -> {
                    //noOp
                })
                .show();
    }

    @Override
    public void onNotificationSent() {
        new MaterialDialog.Builder(getContext())
                .title("Request successfully  sent")
                .positiveColor(getResources().getColor(R.color.blood_red))
                .content("Request sent successfully, A message containing the donor's" +
                        " information will be sent to you once they accept this request")
                .positiveText("Okay")
                .cancelable(false)
                .onPositive((dialog, which) -> {
                    //We are done here
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }).show();
    }
}
