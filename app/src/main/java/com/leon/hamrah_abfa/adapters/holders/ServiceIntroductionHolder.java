package com.leon.hamrah_abfa.adapters.holders;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;

public class ServiceIntroductionHolder extends RecyclerView.ViewHolder {
    public final ImageView imageViewLogo;
    public final ImageView imageViewArrow;
    public final TextView textViewTitle;
    public final TextView textViewIntroduction;
    public final CardView cardView;
    private IClickListener listener;

    public ServiceIntroductionHolder(View view) {
        super(view);
        cardView = view.findViewById(R.id.contribute_card);
        textViewTitle = view.findViewById(R.id.text_view_title);
        textViewIntroduction = view.findViewById(R.id.text_view_introduction);
        imageViewLogo = view.findViewById(R.id.image_view_icon);
        imageViewArrow = view.findViewById(R.id.image_view_arrow);
    }

    public ServiceIntroductionHolder(View view, int position, IClickListener listener) {
        super(view);
        cardView = view.findViewById(R.id.contribute_card);
        textViewTitle = view.findViewById(R.id.text_view_title);
        textViewIntroduction = view.findViewById(R.id.text_view_introduction);
        imageViewLogo = view.findViewById(R.id.image_view_icon);
        imageViewArrow = view.findViewById(R.id.image_view_arrow);
        imageViewArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("position", String.valueOf(position));
//                listener.collapse(12);
            }
        });
    }

    public interface IClickListener {
        void collapse(int position);

        void selectItem(String result);
    }
}
