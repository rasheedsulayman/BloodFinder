package com.r4sh33d.iblood.additionaldetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.models.User;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */

public class AdditionalDetailsFragment extends Fragment {
    private static final String KEY_IS_BLOOD_BANK = "isbloodbank" ;

    public AdditionalDetailsFragment() {
        // Required empty public constructor
    }

    public static AdditionalDetailsFragment newInstance(Boolean isBloodBank , User user) {
        Bundle args = new Bundle();
        args.putBoolean(KEY_IS_BLOOD_BANK , isBloodBank);
        AdditionalDetailsFragment fragment = new AdditionalDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_additional_details, container, false);
        ButterKnife.bind(this , view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.save_button)
    public void onSaveButtonClicked() {

    }

}