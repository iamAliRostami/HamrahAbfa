package com.app.leon.moshtarak.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.R;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    public final ImageView imageView;
    public final TextView textViewDate;
    public final TextView textViewTitle;
    public final TextView textViewSummary;
    public final RelativeLayout relativeLayout;

    public NotificationViewHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.image_view_news);
        textViewDate = view.findViewById(R.id.text_view_date);
        textViewTitle = view.findViewById(R.id.text_view_title);
        textViewSummary = view.findViewById(R.id.text_view_summary);
        relativeLayout = view.findViewById(R.id.relative_layout);
    }
}