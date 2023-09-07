package com.app.leon.moshtarak.adapters.recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.adapters.holders.UsageHistoryFailedViewHolder;
import com.app.leon.moshtarak.fragments.usage_history.AttemptViewModel;
import com.app.leon.moshtarak.R;

import java.util.ArrayList;

public class UsageHistoryFailedAdapter extends RecyclerView.Adapter<UsageHistoryFailedViewHolder> {
    private final LayoutInflater inflater;
    private final ArrayList<AttemptViewModel> usageHistory = new ArrayList<>();
    private int shownNumber = 10;

    public UsageHistoryFailedAdapter(Context context, ArrayList<AttemptViewModel> usageHistory) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.usageHistory.addAll(usageHistory);
    }

    @NonNull
    @Override
    public UsageHistoryFailedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new UsageHistoryFailedViewHolder(inflater.inflate(viewType % 2 == 0 ?
                R.layout.item_usage_failed_history : R.layout.item_usage_history_failed_light, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull UsageHistoryFailedViewHolder holder, int position) {
        holder.textViewNumber.setText(usageHistory.get(position).getCounterNumber());
        holder.textViewDate.setText(usageHistory.get(position).getJalaliDay());
        holder.textViewStatus.setText(usageHistory.get(position).getStatus());

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
