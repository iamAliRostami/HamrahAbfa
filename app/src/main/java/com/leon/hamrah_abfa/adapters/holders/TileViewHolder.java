package com.leon.hamrah_abfa.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.leon.hamrah_abfa.R;

public class TileViewHolder extends RecyclerView.ViewHolder {
    public final ImageView imageViewLogo;
    public final TextView textViewTitle;
    public final MaterialCardView cardView;

    public TileViewHolder(View view) {
        super(view);
        imageViewLogo = view.findViewById(R.id.image_view_icon);
        textViewTitle = view.findViewById(R.id.text_view_title);
        cardView = view.findViewById(R.id.contribute_card);
    }
}