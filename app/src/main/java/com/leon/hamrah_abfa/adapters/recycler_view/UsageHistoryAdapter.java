package com.leon.hamrah_abfa.adapters.recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.UsageHistoryViewHolder;
import com.leon.hamrah_abfa.fragments.usage_history.AttemptViewModel;

import java.util.ArrayList;

public class UsageHistoryAdapter extends RecyclerView.Adapter<UsageHistoryViewHolder> {
    private final LayoutInflater inflater;
    private final ArrayList<AttemptViewModel> usageHistory = new ArrayList<>();
    private int shownNumber = 10;

    public UsageHistoryAdapter(Context context, ArrayList<AttemptViewModel> usageHistory) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.usageHistory.addAll(usageHistory);
    }

    @NonNull
    @Override
    public UsageHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (getItemViewType(viewType) % 2 == 0)
            return new UsageHistoryViewHolder(inflater.inflate(R.layout.item_usage_history, parent, false));
        return new UsageHistoryViewHolder(inflater.inflate(R.layout.item_usage_history_light, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull UsageHistoryViewHolder holder, int position) {
        holder.textViewNumber.setText(usageHistory.get(position).getCounterNumber());
        holder.textViewDate.setText(usageHistory.get(position).getJalaliDay());
        holder.textViewPrice.setText(usageHistory.get(position).getAmount());

    }

    @Override
    public int getItemCount() {
        return Math.min(shownNumber, usageHistory.size());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setShownNumber(int shownNumber) {
        this.shownNumber = shownNumber;
        notifyDataSetChanged();
    }
}
