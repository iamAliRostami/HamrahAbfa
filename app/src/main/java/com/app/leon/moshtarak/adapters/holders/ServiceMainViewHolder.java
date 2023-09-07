package com.app.leon.moshtarak.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.R;

public class ServiceMainViewHolder extends RecyclerView.ViewHolder {
    public final ImageView imageViewLogo;
    public final TextView textViewTitle;
    public final CardView cardView;

    public ServiceMainViewHolder(View view) {
        super(view);
        imageViewLogo = view.findViewById(R.id.image_view_icon);
        textViewTitle = view.findViewById(R.id.text_view_title);
        cardView = view.findViewById(R.id.contribute_card);
    }
}