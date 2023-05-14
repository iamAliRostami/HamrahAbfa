package com.leon.hamrah_abfa.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.leon.hamrah_abfa.R;

public class MenuViewHolder {
    public final ImageView imageViewLogo;
    public final TextView textViewTitle;
    public final CardView cardView;

    public MenuViewHolder(View view) {
        imageViewLogo = view.findViewById(R.id.image_view_icon);
        textViewTitle = view.findViewById(R.id.text_view_title);
        cardView = view.findViewById(R.id.contribute_card);
    }
}
