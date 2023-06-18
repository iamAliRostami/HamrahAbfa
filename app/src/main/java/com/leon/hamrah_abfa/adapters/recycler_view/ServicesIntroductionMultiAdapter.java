package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.ServicesIntroductionBaseAdapter;
import com.leon.hamrah_abfa.adapters.holders.ServiceIntroductionViewHolder;

import java.util.ArrayList;
import java.util.Collections;

public class ServicesIntroductionMultiAdapter extends ServicesIntroductionBaseAdapter {
    private final ArrayList<Integer> selectedServicesId;
    private Integer collapsedPosition;
    public ServicesIntroductionMultiAdapter(Context context, int titleIds, int introductionIds,
                                            int serviceId, int drawableIds) {
        super(context, titleIds, introductionIds, serviceId, drawableIds);
        this.selectedServicesId = new ArrayList<>(Collections.nCopies(titles.size(), 0));
    }

    @NonNull
    @Override
    public ServiceIntroductionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceIntroductionViewHolder(inflater.inflate(R.layout.item_service_collapsed_selected, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceIntroductionViewHolder holder, int position) {
        final int positionHolder = position;
        holder.imageViewLogo.setImageDrawable(drawable.getDrawable(position));
        holder.textViewTitle.setText(titles.get(position));
        holder.textViewIntroduction.setText(introduction.get(position));
        holder.imageViewArrow.setOnClickListener(v -> updateCollapseItem(positionHolder));
        holder.textViewTitle.setOnClickListener(v -> updateSelectedService(positionHolder));
        holder.imageViewLogo.setOnClickListener(v -> updateSelectedService(positionHolder));

        if (selectedServicesId.get(position) == 0) {
            holder.relativeLayout.setBackgroundResource(R.color.light);
        } else {
            holder.relativeLayout.setBackgroundResource(R.drawable.background_service_introduction);
        }
        if (collapsedPosition != null && collapsedPosition == position) {
            holder.viewDivider.setBackgroundResource(R.color.light_gray);
            holder.imageViewArrow.setImageResource(R.drawable.arrow_up);
            holder.textViewIntroduction.setVisibility(View.VISIBLE);
        } else {
            holder.viewDivider.setBackgroundResource(android.R.color.transparent);
            holder.imageViewArrow.setImageResource(R.drawable.arrow_down);
            holder.textViewIntroduction.setVisibility(View.GONE);
        }
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