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

import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.base.BaseFragment;
import com.r4sh33d.iblood.models.BloodPostingData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class bloodPostingSearchResultListFragment extends BaseFragment implements
        PostingsListAdapter.OnBloodPostingItemClickListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private static final String KEY_BLOOD_POSTING_DATA = "bloodposting";

    public static bloodPostingSearchResultListFragment newInstance(ArrayList<BloodPostingData> resultslist) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_BLOOD_POSTING_DATA, resultslist);
        bloodPostingSearchResultListFragment fragment = new bloodPostingSearchResultListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public bloodPostingSearchResultListFragment() {
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
    }
}
