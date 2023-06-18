package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.ServicesIntroductionBaseAdapter;
import com.leon.hamrah_abfa.adapters.holders.ServiceIntroductionViewHolder;

import java.util.ArrayList;

public class ServicesIntroductionSingleAdapter extends ServicesIntroductionBaseAdapter {
    private Integer selectedServices = null;

    public ServicesIntroductionSingleAdapter(Context context, int titleIds, int introductionIds,
                                             int serviceId, int drawableIds) {
        super(context, titleIds, introductionIds, serviceId, drawableIds);
    }

    @NonNull
    @Override
    public ServiceIntroductionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (selectedServices != null && selectedServices == viewType) {
            return new ServiceIntroductionViewHolder(inflater.inflate(R.layout.item_service_collapsed_selected, parent, false));
        } else {
            return new ServiceIntroductionViewHolder(inflater.inflate(R.layout.item_service, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceIntroductionViewHolder holder, int position) {
        holder.imageViewLogo.setImageDrawable(drawable.getDrawable(position));
        holder.textViewTitle.setText(titles.get(position));
        if (selectedServices != null && selectedServices == position)
            holder.textViewIntroduction.setText(introduction.get(position));
    }

    @Override
    public void updateSelectedService(int position) {
        if (selectedServices == null) {
            selectedServices = position;
        } else if (selectedServices == position) {
            selectedServices = null;
        } else {
            final int temp = selectedServices;
            selectedServices = position;
            notifyItemChanged(temp);
        }
        notifyItemChanged(position);
    }

    @Override
    public ArrayList<Integer> selectedServiceId() {
        if (selectedServices == null)
            return new ArrayList<>();
        final ArrayList<Integer> selectedServicesId = new ArrayList<>();
        selectedServicesId.add(servicesId[selectedServices]);
        return selectedServicesId;
    }

    @Override
    public ArrayList<String> selectedServiceTitle() {
        if (selectedServices == null)
            return new ArrayList<>();
        final ArrayList<String> selectedServicesTitle = new ArrayList<>();
        selectedServicesTitle.add(titles.get(selectedServices));
        return selectedServicesTitle;
    }
}
