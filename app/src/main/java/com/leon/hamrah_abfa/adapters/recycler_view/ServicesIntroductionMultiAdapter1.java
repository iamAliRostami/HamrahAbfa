package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.ServiceIntroductionHolder;
import com.leon.hamrah_abfa.infrastructure.ServicesIntroductionBaseAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class ServicesIntroductionMultiAdapter1 extends ServicesIntroductionBaseAdapter {
    private final ArrayList<Integer> selectedServicesId;
    private Integer collapsedPosition;
    public ServicesIntroductionMultiAdapter1(Context context, int titleIds, int introductionIds,
                                             int serviceId, int drawableIds) {
        super(context, titleIds, introductionIds, serviceId, drawableIds);
        this.selectedServicesId = new ArrayList<>(Collections.nCopies(titles.size(), 0));
    }

    @NonNull
    @Override
    public ServiceIntroductionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view;
//        if (collapsedPosition != null && collapsedPosition == viewType) {
//            if (selectedServicesId.get(viewType) == 0)
//                view = inflater.inflate(R.layout.item_service_collapsed, parent, false);
//            else
//                view = inflater.inflate(R.layout.item_service_collapsed_selected, parent, false);
//        } else {
//            if (selectedServicesId.get(viewType) == 0)
//                view = inflater.inflate(R.layout.item_service, parent, false);
//            else
//                view = inflater.inflate(R.layout.item_service_selected, parent, false);
//        }

//        if (selectedServicesId.get(viewType) == 0)
//            view = inflater.inflate(R.layout.item_service_collapsed, parent, false);
//        else
//            view = inflater.inflate(R.layout.item_service_collapsed_selected, parent, false);


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

        final int temp = position;
        holder.textViewTitle.setOnClickListener(v -> updateSelectedService(temp));
        holder.imageViewLogo.setOnClickListener(v -> updateSelectedService(temp));
        holder.imageViewArrow.setOnClickListener(v -> updateCollapseItem(temp));

//        if (selectedServicesId.get(position) > 0 && collapsedPosition != null)
        if (collapsedPosition != null)
            holder.textViewIntroduction.setText(introduction.get(position));
    }

    private void updateCollapseItem(int position) {
        if (collapsedPosition == null) {
            collapsedPosition = position;
        } else if (position == collapsedPosition) {
            collapsedPosition = null;
        } else {
            final int temp = collapsedPosition;
            collapsedPosition = position;
            notifyItemChanged(temp);
        }
        notifyItemChanged(position);
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