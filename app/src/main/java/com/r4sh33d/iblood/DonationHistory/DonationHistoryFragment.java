package com.r4sh33d.iblood.DonationHistory;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.models.UserData;
import com.r4sh33d.iblood.network.Provider;
import com.r4sh33d.iblood.utils.Constants;
import com.r4sh33d.iblood.utils.PrefsUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonationHistoryFragment extends BaseFragment implements
        DonationHistoryContract.View, DonationHistoryAdapter.OnBloodPostingItemClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    DonationHistoryAdapter donationHistoryAdapter;
    DonationHistoryContract.Presenter presenter;

    public DonationHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbarTitle("Donation History");
        presenter = new DonationHistoryPresenter(this, Provider.provideDataRetrofitService());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        donationHistoryAdapter = new DonationHistoryAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(donationHistoryAdapter);
        PrefsUtils prefsUtils = Provider.providePrefManager(getContext());
        UserData userData = prefsUtils.getPrefAsObject(Constants.PREF_KEY_ADDITIONAL_USER_DETAILS, UserData.class);
        presenter.fetchUserDonationHistory(userData);
    }

    @Override
    public void onDonationHistorySuccessfullyFetched(ArrayList<BloodPostingData> dataArrayList) {
        donationHistoryAdapter.updateData(dataArrayList);
    }

    @Override
    public void onBloodPostingItemClicked(BloodPostingData bloodPostingData) {

    }
}
