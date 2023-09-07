package com.app.leon.moshtarak.adapters.holders;

import android.view.View;
import android.widget.ImageView;

import com.app.leon.moshtarak.R;

public class ImageViewHolder {
    public final ImageView imageView;
    public final ImageView imageViewDelete;

    public ImageViewHolder(View view) {
        imageView = view.findViewById(R.id.image_view_incident);
        imageViewDelete = view.findViewById(R.id.image_view_delete);
    }
}