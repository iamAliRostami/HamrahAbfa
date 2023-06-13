package com.leon.hamrah_abfa.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;

public class CheckoutBillViewHolder extends RecyclerView.ViewHolder {
    public final TextView textViewDays;
    public final TextView textViewTo;
    public final TextView textViewPrice;
    public final TextView textViewUsage;
    public final ImageView imageViewUsageType;
    public final ImageView imageViewDetails;
    public final RelativeLayout relativeLayout;

    public CheckoutBillViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewDays = itemView.findViewById(R.id.text_view_days);
        textViewTo = itemView.findViewById(R.id.text_view_to);
        textViewPrice = itemView.findViewById(R.id.text_view_price);
        textViewUsage = itemView.findViewById(R.id.text_view_usage);
        imageViewUsageType = itemView.findViewById(R.id.image_view_usage_type);
        imageViewDetails = itemView.findViewById(R.id.image_view_detail);
        relativeLayout = itemView.findViewById(R.id.relative_layout);
    }
}
