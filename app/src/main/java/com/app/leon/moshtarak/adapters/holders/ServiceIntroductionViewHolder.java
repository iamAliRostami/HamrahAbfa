package com.app.leon.moshtarak.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.R;

public class ServiceIntroductionViewHolder extends RecyclerView.ViewHolder {
    public final ImageView imageViewLogo;
    public final ImageView imageViewArrow;
    public final TextView textViewTitle;
    public final TextView textViewIntroduction;
    public final CardView cardView;
    public final RelativeLayout relativeLayout;
    public final View viewDivider;

    public ServiceIntroductionViewHolder(View view) {
        super(view);
        cardView = view.findViewById(R.id.contribute_card);
        textViewTitle = view.findViewById(R.id.text_view_title);
        textViewIntroduction = view.findViewById(R.id.text_view_introduction);
        imageViewLogo = view.findViewById(R.id.image_view_icon);
        imageViewArrow = view.findViewById(R.id.image_view_arrow);
        viewDivider = view.findViewById(R.id.divider);
        relativeLayout = view.findViewById(R.id.relative_layout);
    }
}