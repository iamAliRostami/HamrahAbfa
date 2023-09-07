package com.app.leon.moshtarak.adapters.holders;

import android.view.View;
import android.widget.TextView;

import com.app.leon.moshtarak.R;

public class PhonebookViewHolder {
    public final TextView textViewTitle;
    public final TextView textViewPhoneNumber;

    public PhonebookViewHolder(View view) {
        textViewTitle = view.findViewById(R.id.text_view_title);
        textViewPhoneNumber = view.findViewById(R.id.text_view_phone_number);
    }
}