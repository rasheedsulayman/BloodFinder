package com.r4sh33d.iblood.postingsresultslist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.models.BloodPostingData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostingsListAdapter extends RecyclerView.Adapter<PostingsListAdapter.PostingsHolder> {
    private OnBloodPostingItemClickListener onBloodPostingItemClickListener;
    ArrayList<BloodPostingData> bloodPostingDataArrayList;

    public PostingsListAdapter(OnBloodPostingItemClickListener onBloodPostingItemClickListener ,
                               ArrayList<BloodPostingData> bloodPostingData) {
        this.onBloodPostingItemClickListener = onBloodPostingItemClickListener;
        this.bloodPostingDataArrayList = bloodPostingData;
    }

    @NonNull
    @Override
    public PostingsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_blood_posting,
                viewGroup, false);
        return new PostingsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostingsHolder postingsHolder, int i) {
         BloodPostingData bloodPostingData = bloodPostingDataArrayList.get(i);
         postingsHolder.donorsBloodTypeTextView.setText(bloodPostingData.donorsBloodType);
         postingsHolder.donorLocationNameTextView.setText(bloodPostingData.donorsName);
         //TODO comeback and set address
    }

    @Override
    public int getItemCount() {
        return bloodPostingDataArrayList.size();
    }

    class PostingsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.donor_name_textview)
        TextView donorNameTextView;
        @BindView(R.id.location_name_textview)
        TextView donorLocationNameTextView;
        @BindView(R.id.blood_type_textview)
        TextView donorsBloodTypeTextView;

        public PostingsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onBloodPostingItemClickListener.onBloodPostingItemClicked(bloodPostingDataArrayList.get(getAdapterPosition()));
        }
    }

    interface OnBloodPostingItemClickListener{
        void onBloodPostingItemClicked(BloodPostingData bloodPostingData);
    }
}