package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.adapters.holders.CheckoutBillViewHolder;

public class CheckoutBillAdapter extends RecyclerView.Adapter<CheckoutBillViewHolder> {
    private final LayoutInflater inflater;

    public CheckoutBillAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public CheckoutBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckoutBillViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutBillViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
