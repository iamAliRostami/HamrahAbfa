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
    private final ArrayList<Integer> selectedServices;
    private final int[] servicesId;
    private final LayoutInflater inflater;
    private final TypedArray drawable;
    private final int serviceType;

    public ServicesIntroductionAdapter(Context context, int titleIds, int introductionIds,
                                       int serviceId, int drawableIds, int serviceType) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.serviceType = serviceType;
        this.titles = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(titleIds)));
        this.introduction = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(introductionIds)));
        this.servicesId = context.getResources().getIntArray(serviceId);
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
        switch (serviceType) {
            case 2:
                selectedServices.set(position, selectedServices.get(position) == 0 ? servicesId[position] : 0);
                break;
            case 1:
            case 0:
            default:
                selectedServices.clear();
                selectedServices.addAll(Collections.nCopies(titles.size(), 0));
                selectedServices.set(position, servicesId[position]);
                int i = 0;
                break;
        }
        notifyItemChanged(position);
    }
}
