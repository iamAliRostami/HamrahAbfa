package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.RequestViewHolder;
import com.leon.hamrah_abfa.fragments.follow_request.RequestInfo;

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
        holder.imageViewRequest.setImageDrawable(icons.getDrawable((int) (Math.log(requests.get(position).requestType) / Math.log(2))));
//        holder.imageViewRequest.setImageDrawable(icons.getDrawable(requests.get(position).requestType));
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

    public int getTrackNumber(int position) {
        return requests.get(position).trackNumber;
    }
}
