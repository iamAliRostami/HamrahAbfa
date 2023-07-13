package com.leon.hamrah_abfa.adapters.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;

public class RequestInfoViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewKey;
    public TextView textViewValue;

    public RequestInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewKey = itemView.findViewById(R.id.text_view_key);
        textViewValue = itemView.findViewById(R.id.text_view_value);
    }
}
