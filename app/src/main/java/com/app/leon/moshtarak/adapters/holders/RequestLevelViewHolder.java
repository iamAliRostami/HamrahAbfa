package com.app.leon.moshtarak.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.R;

public class RequestLevelViewHolder extends RecyclerView.ViewHolder {
    public final TextView textViewTitle;
    public final TextView textViewDate;
    public final TextView textViewLevel;
    public final ImageView imageViewDetail;

    public RequestLevelViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.text_view_title);
        textViewDate = itemView.findViewById(R.id.text_view_date);
        textViewLevel = itemView.findViewById(R.id.text_view_level_number);
        imageViewDetail = itemView.findViewById(R.id.image_view_detail);
    }
}