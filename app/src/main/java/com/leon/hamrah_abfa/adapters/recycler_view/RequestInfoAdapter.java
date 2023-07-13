package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.RequestInfoViewHolder;
import com.leon.hamrah_abfa.fragments.follow_request.TrackItemsInfo;

public class RequestInfoAdapter extends RecyclerView.Adapter<RequestInfoViewHolder> {
    private final TrackItemsInfo trackItemsInfo;

    private final LayoutInflater inflater;

    public RequestInfoAdapter(Context context, TrackItemsInfo trackItemsInfo) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.trackItemsInfo = trackItemsInfo;
    }

    @NonNull
    @Override
    public RequestInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestInfoViewHolder(inflater.inflate(R.layout.item_request_info, parent, false));
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestInfoViewHolder holder, int position) {
        holder.textViewKey.setText(trackItemsInfo.trackItems.get(position).key);
        holder.textViewValue.setText(trackItemsInfo.trackItems.get(position).value);
    }

    @Override
    public int getItemCount() {
        return trackItemsInfo.trackItems.size();
    }
}
