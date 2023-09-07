package com.app.leon.moshtarak.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.R;

public class FAQViewHolder extends RecyclerView.ViewHolder {
    public final ImageView imageViewArrow;
    public final TextView textViewQuestion;
    public final TextView textViewAnswer;
    public final CardView cardView;
    public final RelativeLayout relativeLayout;
    public final View viewDivider;

    public FAQViewHolder(View view) {
        super(view);
        viewDivider = view.findViewById(R.id.divider);
        cardView = view.findViewById(R.id.contribute_card);
        relativeLayout = view.findViewById(R.id.relative_layout);
        imageViewArrow = view.findViewById(R.id.image_view_arrow);
        textViewAnswer = view.findViewById(R.id.text_view_answer);
        textViewQuestion = view.findViewById(R.id.text_view_question);
    }
}