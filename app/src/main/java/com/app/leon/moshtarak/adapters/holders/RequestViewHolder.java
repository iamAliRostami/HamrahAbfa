package com.app.leon.moshtarak.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.R;

public class RequestViewHolder extends RecyclerView.ViewHolder {
    public final TextView textViewDate;
    public final TextView textViewTrackNumber;
    public final TextView textViewRequestType;
    public final ImageView imageViewRequest;
    public final ImageView imageViewDetail;

    public RequestViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewDate = itemView.findViewById(R.id.text_view_date);
        textViewTrackNumber = itemView.findViewById(R.id.text_view_track_number);
        textViewRequestType = itemView.findViewById(R.id.text_view_request_type);
        imageViewRequest = itemView.findViewById(R.id.image_view_request);
        imageViewDetail = itemView.findViewById(R.id.image_view_detail);
    }
}