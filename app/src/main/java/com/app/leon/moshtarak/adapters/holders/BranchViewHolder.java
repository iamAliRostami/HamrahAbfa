package com.app.leon.moshtarak.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.leon.moshtarak.R;

public class BranchViewHolder extends RecyclerView.ViewHolder {
    public final LottieAnimationView animationView;
    public final ImageView imageViewArrow;
    public final TextView textViewName;
    public final TextView textViewFinancialCode;
    public final TextView textViewPhone;
    public final TextView textViewFax;
    public final TextView textViewPostal;
    public final TextView textViewAddress;

    public BranchViewHolder(@NonNull View itemView) {
        super(itemView);
        animationView = itemView.findViewById(R.id.lottie_animation_view_address);
        imageViewArrow = itemView.findViewById(R.id.image_view_arrow);

        textViewName = itemView.findViewById(R.id.text_view_name);
        textViewFinancialCode = itemView.findViewById(R.id.text_view_financial);
        textViewPhone = itemView.findViewById(R.id.text_view_phone);
        textViewFax = itemView.findViewById(R.id.text_view_fax);
        textViewPostal = itemView.findViewById(R.id.text_view_postal);
        textViewAddress = itemView.findViewById(R.id.text_view_address);
    }
}