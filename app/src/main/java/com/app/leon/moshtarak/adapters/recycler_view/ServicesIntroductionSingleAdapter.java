package com.app.leon.moshtarak.adapters.recycler_view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.app.leon.moshtarak.adapters.base_adapter.ServicesIntroductionBaseAdapter;
import com.app.leon.moshtarak.adapters.holders.ServiceIntroductionViewHolder;
import com.app.leon.moshtarak.R;

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
//        return new ServiceIntroductionViewHolder(inflater.inflate((selectedServices != null &&
//                selectedServices == viewType) ? R.layout.item_service_collapsed_selected :
//                R.layout.item_service, parent, false));
        return new ServiceIntroductionViewHolder(inflater.inflate(R.layout.item_service_collapsed_selected,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceIntroductionViewHolder holder, int position) {
        holder.imageViewLogo.setImageDrawable(drawable.getDrawable(position));
        holder.textViewTitle.setText(titles.get(position));
//        if (selectedServices != null && selectedServices == position)
        holder.textViewIntroduction.setText(introduction.get(position));


//        if (selectedServicesId.get(position) == 0) {
//            holder.relativeLayout.setBackgroundResource(R.color.light);
//        } else {
//            holder.relativeLayout.setBackgroundResource(R.drawable.background_service_introduction);
//        }
        if (selectedServices != null && selectedServices == position) {
            holder.textViewIntroduction.setVisibility(View.VISIBLE);
            holder.imageViewArrow.setImageResource(R.drawable.arrow_up);
            holder.viewDivider.setBackgroundResource(R.color.light_gray);
            holder.relativeLayout.setBackgroundResource(R.drawable.background_service_introduction);
        } else {
            holder.textViewIntroduction.setVisibility(View.GONE);
            holder.imageViewArrow.setImageResource(R.drawable.arrow_down);
            holder.viewDivider.setBackgroundResource(android.R.color.transparent);
            holder.relativeLayout.setBackgroundResource(R.color.light);
        }
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
