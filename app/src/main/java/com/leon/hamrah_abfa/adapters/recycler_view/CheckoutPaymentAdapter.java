package com.leon.hamrah_abfa.adapters.recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.CheckoutPaymentViewHolder;
import com.leon.hamrah_abfa.fragments.checkout.CheckoutPaymentViewModel;

import java.util.ArrayList;

public class CheckoutPaymentAdapter extends RecyclerView.Adapter<CheckoutPaymentViewHolder> {
    private final LayoutInflater inflater;
    private final ArrayList<CheckoutPaymentViewModel> payments = new ArrayList<>();
    private int shownNumber = 10;

    public CheckoutPaymentAdapter(Context context, ArrayList<CheckoutPaymentViewModel> payments) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.payments.addAll(payments);
    }

    @NonNull
    @Override
    public CheckoutPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (getItemViewType(viewType) % 2 == 0)
            return new CheckoutPaymentViewHolder(inflater.inflate(R.layout.item_checkout_payment_light, parent, false));
        return new CheckoutPaymentViewHolder(inflater.inflate(R.layout.item_checkout_payment, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutPaymentViewHolder holder, int position) {
//        if (position % 2 == 0)
//            holder.relativeLayout.setBackgroundResource(R.drawable.background_last_bill_light);
//        else
//            holder.relativeLayout.setBackgroundResource(R.drawable.background_last_bill);
        holder.textViewPrice.setText(payments.get(position).amount);
        holder.textViewBank.setText(payments.get(position).bank);
        holder.textViewPaymentType.setText(payments.get(position).method);
        holder.textViewDate.setText(payments.get(position).day);

        if (!payments.get(position).submitType)
            holder.imageViewSubmitType.setImageResource(R.drawable.ic_disapproval);

    }

    @Override
    public int getItemCount() {
        return Math.min(shownNumber, payments.size());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setShownNumber(int shownNumber) {
        this.shownNumber = shownNumber;
        notifyDataSetChanged();
    }
}
