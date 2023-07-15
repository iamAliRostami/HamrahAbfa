package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.RequestInfoViewHolder;
import com.leon.hamrah_abfa.fragments.follow_request.TrackItemInfo;

import java.util.ArrayList;

public class RequestDetailInfoAdapter extends RecyclerView.Adapter<RequestInfoViewHolder> {
    private final ArrayList<TrackItemInfo> trackItems = new ArrayList<>();

    private final LayoutInflater inflater;

    public RequestDetailInfoAdapter(Context context, ArrayList<TrackItemInfo> trackItems) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.trackItems.addAll(trackItems);
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
        holder.textViewKey.setText(trackItems.get(position).key.trim());
        if (trackItems.get(position).value != null)
            holder.textViewValue.setText(trackItems.get(position).value.trim());
    }

    @Override
    public int getItemCount() {
        return trackItems.size();
    }
}
