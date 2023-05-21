package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.RequestViewHolder;
import com.leon.hamrah_abfa.tables.Request;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestViewHolder> {
    private final ArrayList<Request> requests = new ArrayList<>();
    private final TypedArray icons;
    private final LayoutInflater inflater;

    public RequestAdapter(Context context, ArrayList<Request> requests) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icons = context.getResources().obtainTypedArray(R.array.services_main_icons);
        setRequests(requests);
    }

    private void setRequests(ArrayList<Request> requests) {
        this.requests.addAll(requests);
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RequestViewHolder(inflater.inflate(R.layout.item_request, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        holder.textViewDate.setText(requests.get(position).getDate());
        holder.textViewTrackNumber.setText(requests.get(position).getTrackNumber());
        holder.textViewRequestType.setText(requests.get(position).getTitle());
        holder.imageViewRequest.setImageDrawable(icons.getDrawable(requests.get(position).getServiceType()));
//        holder.imageViewDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShowFragmentDialogOnce(inflater.getContext(), FOLLOW_REQUEST_LEVEL.getValue(), FollowRequestLevelsFragment.newInstance());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
}
