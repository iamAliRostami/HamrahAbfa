package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.RequestLevelViewHolder;
import com.leon.hamrah_abfa.tables.RequestLevel;

import java.util.ArrayList;

public class RequestLevelAdapter extends RecyclerView.Adapter<RequestLevelViewHolder> {
    private final LayoutInflater inflater;

    private final ArrayList<RequestLevel> requestLevels = new ArrayList<>();

    public RequestLevelAdapter(Context context, ArrayList<RequestLevel> requestLevels) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.requestLevels.addAll(requestLevels);
    }

    @NonNull
    @Override
    public RequestLevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (requestLevels.get(viewType).isCurrentLevel())
            return new RequestLevelViewHolder(inflater.inflate(R.layout.item_request_current_level, parent, false));
        else if (!requestLevels.get(viewType).isDoneLevel()) {
            return new RequestLevelViewHolder(inflater.inflate(R.layout.item_request_waiting_level, parent, false));
        }
        return new RequestLevelViewHolder(inflater.inflate(R.layout.item_request_done_level, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestLevelViewHolder holder, int position) {
        holder.textViewLevel.setText(String.valueOf(position));
        holder.textViewDate.setText(requestLevels.get(position).getDate());
        holder.textViewTitle.setText(requestLevels.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return requestLevels.size();
    }
}
