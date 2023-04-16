package com.leon.hamrah_abfa.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.ServiceMainHolder;

import java.util.ArrayList;
import java.util.Arrays;

public class ServicesMainAdapter extends RecyclerView.Adapter<ServiceMainHolder> {

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
    public ServiceMainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = inflater.inflate(R.layout.item_service_main,parent,false);
        return new ServiceMainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceMainHolder holder, int position) {
        holder.imageViewLogo.setImageDrawable(drawable.getDrawable(position));
        holder.textViewTitle.setText(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
}
