package com.app.leon.moshtarak.adapters.recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.adapters.holders.CheckoutPaymentViewHolder;
import com.app.leon.moshtarak.fragments.checkout.CheckoutPaymentViewModel;
import com.app.leon.moshtarak.R;

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
        return new CheckoutPaymentViewHolder(inflater.inflate(getItemViewType(viewType) % 2 == 0 ?
                R.layout.item_checkout_payment_light : R.layout.item_checkout_payment, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CheckoutPaymentViewHolder holder, int position) {
        CheckoutPaymentViewModel payment = payments.get(position);
        holder.textViewPrice.setText(String.format("%,d", payment.amount));
        holder.textViewBank.setText(payment.bank);
        holder.textViewPaymentType.setText(payment.method);
        holder.textViewDate.setText(payment.day);

        if (payments.get(position).isTemp)
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
