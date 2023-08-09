package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.ServiceIntroductionViewHolder;

import java.util.ArrayList;
import java.util.Arrays;

public class CitizenAdapter extends RecyclerView.Adapter<ServiceIntroductionViewHolder> {
    public final ArrayList<String> titles;
    public final ArrayList<String> introduction;
    public final LayoutInflater inflater;
    public final TypedArray drawable;
    private Integer selected = null;

    public CitizenAdapter(Context context, int titleIds, int introductionIds,
                          int drawableIds) {

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.titles = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(titleIds)));
        this.introduction = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(introductionIds)));
        this.drawable = context.getResources().obtainTypedArray(drawableIds);
    }

    @NonNull
    @Override
    public ServiceIntroductionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServiceIntroductionViewHolder(inflater.inflate((selected != null &&
                selected == viewType) ? R.layout.item_service_collapsed_selected :
                R.layout.item_service, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceIntroductionViewHolder holder, int position) {
        holder.imageViewLogo.setImageDrawable(drawable.getDrawable(position));
        holder.textViewTitle.setText(titles.get(position));
        if (selected != null && selected == position)
            holder.textViewIntroduction.setText(introduction.get(position));
    }

    public void updateSelectedService(int position) {
        if (selected == null) {
            selected = position;
        } else if (selected == position) {
            selected = null;
        } else {
            final int temp = selected;
            selected = position;
            notifyItemChanged(temp);
        }
        notifyItemChanged(position);
    }

    public String selectedTitle() {
        if (selected == null)
            return null;
        return titles.get(selected);
    }


    @Override
    public int getItemCount() {
        return titles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
