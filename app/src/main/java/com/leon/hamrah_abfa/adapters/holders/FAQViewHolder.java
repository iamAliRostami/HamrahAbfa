package com.leon.hamrah_abfa.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;

public class FAQViewHolder extends RecyclerView.ViewHolder {
    public final ImageView imageViewArrow;
    public final TextView textViewQuestion;
    public final TextView textViewAnswer;
    public final CardView cardView;
    public final RelativeLayout relativeLayout;
    public final View viewDivider;

    public FAQViewHolder(View view) {
        super(view);
        textViewQuestion = view.findViewById(R.id.text_view_question);
        imageViewArrow = view.findViewById(R.id.image_view_arrow);

        cardView = view.findViewById(R.id.contribute_card);
        textViewAnswer = view.findViewById(R.id.text_view_answer);
        viewDivider = view.findViewById(R.id.divider);
        relativeLayout = view.findViewById(R.id.relative_layout);
    }
}
