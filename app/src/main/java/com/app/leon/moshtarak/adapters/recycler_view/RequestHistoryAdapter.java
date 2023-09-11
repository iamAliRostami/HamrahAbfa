package com.app.leon.moshtarak.adapters.recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.adapters.holders.RequestViewHolder;
import com.app.leon.moshtarak.fragments.follow_request.RequestInfo;
import com.app.leon.moshtarak.R;

import java.util.ArrayList;

public class RequestHistoryAdapter extends RecyclerView.Adapter<RequestViewHolder> {
    private final ArrayList<RequestInfo> requests = new ArrayList<>();
    private final LayoutInflater inflater;
    private final TypedArray icons;

    public RequestHistoryAdapter(Context context, ArrayList<RequestInfo> requests) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icons = context.getResources().obtainTypedArray(R.array.services_main_icons);
        this.requests.addAll(requests);
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestViewHolder(inflater.inflate(R.layout.item_request, parent, false));
    }

    @Override

    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        holder.textViewDate.setText(requests.get(position).jalaliDay);
        holder.textViewTrackNumber.setText(String.valueOf(requests.get(position).trackNumber));
        holder.textViewRequestType.setText(requests.get(position).requestTitle);
        holder.textViewRequestType.setSelected(true);
        holder.imageViewRequest.setImageDrawable(icons.getDrawable((int)
                (Math.log(requests.get(position).requestType) / Math.log(2))));
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public int getTrackNumber(int position) {
        return requests.get(position).trackNumber;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<RequestInfo> requestInfoTemp) {
        requests.clear();
        requests.addAll(requestInfoTemp);
        notifyDataSetChanged();
    }
}
