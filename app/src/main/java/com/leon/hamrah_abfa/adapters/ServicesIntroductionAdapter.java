package com.leon.hamrah_abfa.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.ServiceIntroductionHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ServicesIntroductionAdapter extends RecyclerView.Adapter<ServiceIntroductionHolder> {
    private final ArrayList<String> titles;
    private final ArrayList<String> introduction;
    private final TypedArray drawable;
    private final LayoutInflater inflater;
    private final ArrayList<Integer> selectedServices;
//    private final ArrayList<Integer> servicesId;

    public ServicesIntroductionAdapter(Context context, int titleIds, int introductionIds, int drawableIds) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.titles = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(titleIds)));
        this.introduction = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(introductionIds)));
        this.selectedServices = new ArrayList<>(Collections.nCopies(titles.size(), 0));
        this.drawable = context.getResources().obtainTypedArray(drawableIds);
    }

    @NonNull
    @Override
    public ServiceIntroductionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        if (selectedServices.get(viewType) == 0)
            view = inflater.inflate(R.layout.item_service, parent, false);
        else
            view = inflater.inflate(R.layout.item_service_selected, parent, false);
        return new ServiceIntroductionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceIntroductionHolder holder, int position) {
        holder.imageViewLogo.setImageDrawable(drawable.getDrawable(position));
        holder.textViewTitle.setText(titles.get(position));
        if (selectedServices.get(position) > 0)
            holder.textViewIntroduction.setText(introduction.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateSelectedService(int position) {
        selectedServices.set(position, selectedServices.get(position) == 0 ? 1 : 0);
        notifyItemChanged(position);
    }
}
