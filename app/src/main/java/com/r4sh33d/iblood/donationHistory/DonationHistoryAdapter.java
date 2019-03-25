package com.r4sh33d.iblood.donationHistory;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.r4sh33d.iblood.R;
import com.r4sh33d.iblood.models.BloodPostingData;
import com.r4sh33d.iblood.utils.DateUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.r4sh33d.iblood.utils.Constants.BloodPostingStatus;

public class DonationHistoryAdapter extends RecyclerView.Adapter<DonationHistoryAdapter.PostingsHolder> {
    private OnBloodPostingItemClickListener onBloodPostingItemClickListener;
    ArrayList<BloodPostingData> bloodPostingDataArrayList;

    public DonationHistoryAdapter(OnBloodPostingItemClickListener onBloodPostingItemClickListener,
                                  ArrayList<BloodPostingData> bloodPostingData) {
        this.onBloodPostingItemClickListener = onBloodPostingItemClickListener;
        this.bloodPostingDataArrayList = bloodPostingData;
    }

    @NonNull
    @Override
    public PostingsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_donation_history,
                viewGroup, false);
        return new PostingsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostingsHolder postingsHolder, int i) {
        BloodPostingData bloodPostingData = bloodPostingDataArrayList.get(i);
        postingsHolder.donorsBloodTypeTextView.setText(bloodPostingData.donorsBloodType);
        // postingsHolder.donorNameTextView.setText(bloodPostingData.donorsName);
        postingsHolder.dateTextView.setText(DateUtils.getRelativeSentFromMessageWithTime
                (Long.parseLong(bloodPostingData.creationTime)));
        switch (bloodPostingData.status) {
            case BloodPostingStatus.ACCEPTED:
                postingsHolder.statusTextView.setText("Accepted");
                postingsHolder.statusTextView.setTextColor(Color.GREEN);
                break;
            case BloodPostingStatus.PENDING:
                postingsHolder.statusTextView.setText("Pending");
                postingsHolder.statusTextView.setTextColor(postingsHolder.itemView.getContext().
                        getResources().getColor(R.color.yellow));
                break;
        }
    }

    void updateData(ArrayList<BloodPostingData> bloodPostingDataList) {
        this.bloodPostingDataArrayList = bloodPostingDataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return bloodPostingDataArrayList.size();
    }

    class PostingsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.blood_type_textview)
        TextView donorsBloodTypeTextView;

        /*@BindView(R.id.donor_name_textview)
        TextView donorNameTextView;*/

        @BindView(R.id.date_textview)
        TextView dateTextView;
        @BindView(R.id.status_textview)
        TextView statusTextView;

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

    interface OnBloodPostingItemClickListener {
        void onBloodPostingItemClicked(BloodPostingData bloodPostingData);
    }
}
