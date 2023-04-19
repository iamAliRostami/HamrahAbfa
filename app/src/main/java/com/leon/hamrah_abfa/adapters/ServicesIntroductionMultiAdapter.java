package com.leon.hamrah_abfa.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.ServiceIntroductionHolder;
import com.leon.hamrah_abfa.infrastructure.ServicesIntroductionBaseAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class ServicesIntroductionMultiAdapter extends ServicesIntroductionBaseAdapter {
    private final ArrayList<Integer> selectedServicesId;

    public ServicesIntroductionMultiAdapter(Context context, int titleIds, int introductionIds,
                                            int serviceId, int drawableIds) {
        super(context, titleIds, introductionIds, serviceId, drawableIds);
        this.selectedServicesId = new ArrayList<>(Collections.nCopies(titles.size(), 0));
    }

    @NonNull
    @Override
    public ServiceIntroductionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
        if (selectedServicesId.get(viewType) == 0)
            view = inflater.inflate(R.layout.item_service, parent, false);
        else
            view = inflater.inflate(R.layout.item_service_selected, parent, false);
        return new ServiceIntroductionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceIntroductionHolder holder, int position) {
        holder.imageViewLogo.setImageDrawable(drawable.getDrawable(position));
        holder.textViewTitle.setText(titles.get(position));
        if (selectedServicesId.get(position) > 0)
            holder.textViewIntroduction.setText(introduction.get(position));
    }

    @Override
    public void updateSelectedService(int position) {
        selectedServicesId.set(position, selectedServicesId.get(position) == 0 ? servicesId[position] : 0);
        notifyItemChanged(position);
    }

    @Override
    public ArrayList<Integer> selectedServiceId() {
        final ArrayList<Integer> selectedServicesId = new ArrayList<>();
        for (int i : this.selectedServicesId) {
            if (i != 0)
                selectedServicesId.add(i);
        }
        return selectedServicesId;
    }

    @Override
    public ArrayList<String> selectedServiceTitle() {
        final ArrayList<String> selectedServicesTitle = new ArrayList<>();
        for (int i = 0; i < this.titles.size(); i++) {
            if (this.selectedServicesId.get(i) != 0)
                selectedServicesTitle.add(titles.get(i));
        }
        return selectedServicesTitle;
    }
}
