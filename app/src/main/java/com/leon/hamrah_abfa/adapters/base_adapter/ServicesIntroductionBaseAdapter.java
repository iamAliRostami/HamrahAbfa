package com.leon.hamrah_abfa.adapters.base_adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.adapters.holders.ServiceIntroductionViewHolder;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class ServicesIntroductionBaseAdapter extends RecyclerView.Adapter<ServiceIntroductionViewHolder> {
    public final ArrayList<String> titles;
    public final ArrayList<String> introduction;
    public final LayoutInflater inflater;
    public final TypedArray drawable;
    public final int[] servicesId;

    public ServicesIntroductionBaseAdapter(Context context, int titleIds, int introductionIds,
                                           int serviceId, int drawableIds) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.titles = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(titleIds)));
        this.introduction = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(introductionIds)));
        this.servicesId = context.getResources().getIntArray(serviceId);
        this.drawable = context.getResources().obtainTypedArray(drawableIds);
    }

    public abstract void updateSelectedService(int position);

    public abstract ArrayList<Integer> selectedServiceId();

    public abstract ArrayList<String> selectedServiceTitle();

    @Override
    public int getItemCount() {
        return titles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
