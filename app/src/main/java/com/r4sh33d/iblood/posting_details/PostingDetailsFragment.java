package com.r4sh33d.iblood.posting_details;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.models.BloodPostingData;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostingDetailsFragment extends Fragment {

    private static final  String KEY_BLOOD_POSTING_DATA = "blood_posting_data";

    public static PostingDetailsFragment newInstance(BloodPostingData  bloodPostingData) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_BLOOD_POSTING_DATA , bloodPostingData);
        PostingDetailsFragment fragment = new PostingDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PostingDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_posting_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
