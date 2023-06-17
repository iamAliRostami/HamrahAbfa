package com.leon.hamrah_abfa.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;

public class CheckoutPaymentViewHolder extends RecyclerView.ViewHolder {
    public final TextView textViewDate;
    public final TextView textViewPrice;
    public final TextView textViewBank;
    public final TextView textViewPaymentType;
    public final ImageView imageViewSubmitType;
    public final RelativeLayout relativeLayout;

    public CheckoutPaymentViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewDate = itemView.findViewById(R.id.text_view_date);
        textViewPrice = itemView.findViewById(R.id.text_view_price);
        textViewPaymentType = itemView.findViewById(R.id.text_view_payment_type);
        imageViewSubmitType = itemView.findViewById(R.id.image_view_submit_type);
        textViewBank = itemView.findViewById(R.id.text_view_bank);
        relativeLayout = itemView.findViewById(R.id.relative_layout);
    }
}
