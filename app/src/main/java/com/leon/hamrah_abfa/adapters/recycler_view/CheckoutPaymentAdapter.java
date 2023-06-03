package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.adapters.holders.CheckoutPaymentViewHolder;

public class CheckoutPaymentAdapter extends RecyclerView.Adapter<CheckoutPaymentViewHolder> {
    private final LayoutInflater inflater;

    public CheckoutPaymentAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CheckoutPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckoutPaymentViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutPaymentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
