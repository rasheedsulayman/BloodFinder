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
public class UserTypeSelectionFragment extends Fragment {


    public UserTypeSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_type_selection, container, false);
        ButterKnife.bind(this , view);
        return view;
    }

    @OnClick(R.id.blood_banks_button)
    public void onBloodBankButtonClicked(){}

    @OnClick(R.id.individual_users_button)
    public void onBloodDonorButtonClicked(){}


}
