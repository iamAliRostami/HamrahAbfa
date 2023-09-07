package com.app.leon.moshtarak.adapters.recycler_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.adapters.holders.ServiceMainViewHolder;
import com.app.leon.moshtarak.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ServicesMainAdapter extends RecyclerView.Adapter<ServiceMainViewHolder> {
    private final ArrayList<String> titles;
    private final TypedArray drawable;
    private final LayoutInflater inflater;

    public ServicesMainAdapter(Context context, int titleIds, int drawableIds) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.titles = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(titleIds)));
        this.drawable = context.getResources().obtainTypedArray(drawableIds);
    }

    @NonNull
    @Override
    public ServiceMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceMainViewHolder(inflater.inflate(R.layout.item_service_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceMainViewHolder holder, int position) {
        holder.imageViewLogo.setImageDrawable(drawable.getDrawable(position));
        holder.textViewTitle.setText(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
}
