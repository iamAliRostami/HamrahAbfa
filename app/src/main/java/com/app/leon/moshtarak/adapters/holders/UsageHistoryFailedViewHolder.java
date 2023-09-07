package com.app.leon.moshtarak.adapters.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.R;

public class UsageHistoryFailedViewHolder extends RecyclerView.ViewHolder {
    public final TextView textViewDate;
    public final TextView textViewNumber;
    public final TextView textViewStatus;

    public UsageHistoryFailedViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewDate = itemView.findViewById(R.id.text_view_date);
        textViewNumber = itemView.findViewById(R.id.text_view_number);
        textViewStatus = itemView.findViewById(R.id.text_view_status);
    }
}