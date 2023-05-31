package com.leon.hamrah_abfa.adapters.holders;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.leon.hamrah_abfa.R;

public class PayBillViewHolder extends RecyclerView.ViewHolder {
    public final LinearLayoutCompat linearLayoutContent;
    public final TextView textViewDeadline;
    public final TextView textViewPrice;
    public final MaterialCheckBox textViewNickname;

    public PayBillViewHolder(View itemView) {
        super(itemView);
        linearLayoutContent = itemView.findViewById(R.id.linear_layout_content);
        textViewDeadline = itemView.findViewById(R.id.text_view_deadline);
        textViewPrice = itemView.findViewById(R.id.text_view_price);
        textViewNickname = itemView.findViewById(R.id.text_view_nick_name);
    }
}
