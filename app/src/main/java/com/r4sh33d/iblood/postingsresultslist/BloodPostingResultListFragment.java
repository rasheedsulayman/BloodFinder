package com.r4sh33d.iblood.postingsresultslist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.BloodPostingData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */

public class BloodPostingResultListFragment extends BaseFragment implements
        PostingsListAdapter.OnBloodPostingItemClickListener, BloodPostingResultListContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private static final String KEY_BLOOD_POSTING_DATA = "bloodposting";
    BloodPostingResultListContract.Presenter presenter;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<BloodPostingData> resultsList = getArguments().getParcelableArrayList(KEY_BLOOD_POSTING_DATA);
        recyclerView.setAdapter(new PostingsListAdapter(this, resultsList));
    }

    @Override
    public void onBloodPostingItemClicked(BloodPostingData bloodPostingData) {
        //TODO come back and open a more detailed screen... or show a diaglog for sending donation request.
        new MaterialDialog.Builder(getContext())
                .title("Send Request?")
                .content("A blood donation request will be sent to " + bloodPostingData.donorsName)
                .positiveText("Okay")
                .negativeText("Cancel")
                .onPositive((dialog, which) -> {
                      //TODO come and send the request
                })
                .onNegative((dialog, which) -> {
                    //noOp
                })
                .show();
    }

    @Override
    public void onNotificationSent(BloodPostingData bloodPostingData) {

    }
}
