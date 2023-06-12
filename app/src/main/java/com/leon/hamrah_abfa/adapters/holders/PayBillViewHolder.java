package com.leon.hamrah_abfa.adapters.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;

public class PayBillViewHolder extends RecyclerView.ViewHolder {
    public final RelativeLayout relativeLayoutContent;
    public final TextView textViewNickname;
    public final TextView textViewDeadline;
    public final TextView textViewPrice;
    public final ImageView imageView;

    public PayBillViewHolder(View itemView) {
        super(itemView);
        relativeLayoutContent = itemView.findViewById(R.id.relative_layout_content);
        textViewDeadline = itemView.findViewById(R.id.text_view_deadline);
        textViewPrice = itemView.findViewById(R.id.text_view_price);
        textViewNickname = itemView.findViewById(R.id.text_view_nick_name);
        imageView = itemView.findViewById(R.id.image_view);
    }
}
