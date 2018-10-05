package com.r4sh33d.iblood;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.r4sh33d.iblood.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends BaseFragment {


    public DashBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);
        ButterKnife.bind(this , view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDrawerIconToHome();
    }

    @OnClick(R.id.find_donors_button)
    public void onClickFindDonorsButton(){
        replaceFragment(new BloodRequestFragment());
    }

    @OnClick(R.id.upload_blood_availability_button)
    public void onClickUploadBloodAvailaibilityButton(){
        replaceFragment(new UploadBloodAvailabilityFragment());
    }

}
