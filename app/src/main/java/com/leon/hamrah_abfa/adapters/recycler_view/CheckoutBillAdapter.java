package com.leon.hamrah_abfa.adapters.recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.CheckoutBillViewHolder;
import com.leon.hamrah_abfa.fragments.checkout.CheckoutBillViewModel;

import java.util.ArrayList;

public class CheckoutBillAdapter extends RecyclerView.Adapter<CheckoutBillViewHolder> {
    private final LayoutInflater inflater;
    private final ArrayList<CheckoutBillViewModel> bills = new ArrayList<>();
    private int shownNumber = 10;

    public CheckoutBillAdapter(Context context, ArrayList<CheckoutBillViewModel> bills) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bills.addAll(bills);
    }

    @NonNull
    @Override
    public CheckoutBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (getItemViewType(viewType) % 2 == 0)
            return new CheckoutBillViewHolder(inflater.inflate(R.layout.item_checkout_bill_light, parent, false));
        return new CheckoutBillViewHolder(inflater.inflate(R.layout.item_checkout_bill, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CheckoutBillViewHolder holder, int position) {
        final CheckoutBillViewModel bill = bills.get(position);
        if (bill.usageType == 2)
            holder.imageViewUsageType.setImageResource(R.drawable.ic_high);
        else if (bill.usageType == 1)
            holder.imageViewUsageType.setImageResource(R.drawable.ic_low);
        holder.textViewTo.setText(bill.day);

        holder.textViewPrice.setText(String.format("%,d", bill.amount));
        holder.textViewDays.setText(String.valueOf(bill.duration));
        holder.textViewUsage.setText(String.valueOf(bill.usage));
    }

    @Override
    public int getItemCount() {
        return Math.min(shownNumber, bills.size());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setShownNumber(int shownNumber) {
        this.shownNumber = shownNumber;
        notifyDataSetChanged();
    }

    public int getZoneId(int position) {
        return bills.get(position).zoneId;
    }
    public int getId(int position) {
        return bills.get(position).id;
    }
}
