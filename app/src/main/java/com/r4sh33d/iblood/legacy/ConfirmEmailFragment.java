package com.r4sh33d.iblood.legacy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.r4sh33d.iblood.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmEmailFragment extends Fragment {


    public ConfirmEmailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_email, container, false);
    }

}
