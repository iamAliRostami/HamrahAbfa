package com.leon.hamrah_abfa.adapters.holders;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;

public class ActiveSessionViewHolder extends RecyclerView.ViewHolder {
    public final AppCompatTextView textViewDevice;
    public final AppCompatTextView textViewIP;
    public final AppCompatTextView textViewMobile;
    public final AppCompatTextView textViewLastLogin;

    public ActiveSessionViewHolder(View view) {
        super(view);
        textViewDevice = view.findViewById(R.id.text_view_device_name);
        textViewMobile = view.findViewById(R.id.text_view_mobile);
        textViewLastLogin = view.findViewById(R.id.text_view_device_last_login);
        textViewIP = view.findViewById(R.id.text_view_ip);
    }
}