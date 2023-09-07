package com.app.leon.moshtarak.adapters.holders;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.R;

public class ActiveSessionViewHolder extends RecyclerView.ViewHolder {
    public final AppCompatTextView textViewIP;
    public final AppCompatTextView textViewDevice;
    public final AppCompatTextView textViewValidate;
    public final AppCompatTextView textViewLastLogin;

    public ActiveSessionViewHolder(View view) {
        super(view);
        textViewIP = view.findViewById(R.id.text_view_ip);
        textViewValidate = view.findViewById(R.id.text_view_validate);
        textViewDevice = view.findViewById(R.id.text_view_device_name);
        textViewLastLogin = view.findViewById(R.id.text_view_device_last_login);
    }
}