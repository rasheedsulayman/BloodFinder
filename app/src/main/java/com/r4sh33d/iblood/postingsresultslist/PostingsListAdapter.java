package com.r4sh33d.iblood.postingsresultslist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.r4sh33d.iblood.models.BloodPostingData;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class PostingsListAdapter  extends RecyclerView.Adapter<PostingsListAdapter.PostingsHolder>{
    ArrayList<BloodPostingData> bloodPostingDataArrayList;

    public PostingsListAdapter(ArrayList<BloodPostingData> bloodPostingData) {
        this.bloodPostingDataArrayList = bloodPostingData;
    }

    @NonNull
    @Override
    public PostingsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostingsHolder postingsHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return bloodPostingDataArrayList.size();
    }

    class PostingsHolder extends RecyclerView.ViewHolder {

        public PostingsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            
        }
    }


}
