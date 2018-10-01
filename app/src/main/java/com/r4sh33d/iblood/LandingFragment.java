package com.r4sh33d.iblood;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandingFragment extends Fragment {


    public LandingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_landing, container, false);
        ButterKnife.bind(this , view);
        return view;
    }

    @OnClick(R.id.blood_banks_button)
    public void onBloodBankButtonClicked(){}

    @OnClick(R.id.blood_seeker_button)
    public void onBloodSeekerButtonClicked(){}

    @OnClick(R.id.blood_donor_button)
    public void onBloodDonorButtonClicked(){}


}
