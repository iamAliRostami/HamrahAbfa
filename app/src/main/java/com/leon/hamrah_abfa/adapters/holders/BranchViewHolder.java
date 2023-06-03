package com.leon.hamrah_abfa.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;

public class BranchViewHolder extends RecyclerView.ViewHolder {
    public final ImageView imageViewAddress;
    public final ImageView imageViewArrow;
    public final TextView textViewName;
    public final TextView textViewManager;
    public final TextView textViewFinancialCode;
    public final TextView textViewZone;
    public final TextView textViewPhone1;
    public final TextView textViewFax;
    public final TextView textViewPostal;
    public final TextView textViewAddress;

    public BranchViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewAddress = itemView.findViewById(R.id.image_view_address);
        imageViewArrow = itemView.findViewById(R.id.image_view_arrow);

        textViewName = itemView.findViewById(R.id.text_view_name);
        textViewManager = itemView.findViewById(R.id.text_view_manager);
        textViewZone = itemView.findViewById(R.id.text_view_zone);
        textViewFinancialCode = itemView.findViewById(R.id.text_view_financial);
        textViewPhone1 = itemView.findViewById(R.id.text_view_phone_1);
        textViewFax = itemView.findViewById(R.id.text_view_fax);
        textViewPostal = itemView.findViewById(R.id.text_view_postal);
        textViewAddress = itemView.findViewById(R.id.text_view_address);
    }
}
