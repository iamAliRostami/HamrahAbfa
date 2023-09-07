package com.app.leon.moshtarak.adapters.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.R;

public class RequestInfoViewHolder extends RecyclerView.ViewHolder {
    public final TextView textViewKey;
    public final TextView textViewValue;

    public RequestInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewKey = itemView.findViewById(R.id.text_view_key);
        textViewValue = itemView.findViewById(R.id.text_view_value);
    }
}