package com.app.leon.moshtarak.adapters.base_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.app.leon.moshtarak.adapters.holders.MenuViewHolder;
import com.app.leon.moshtarak.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuAdapter extends BaseAdapter {
    private final ArrayList<String> titles;
    private final TypedArray drawable;
    private final LayoutInflater inflater;

    public MenuAdapter(Context context, int titleIds, int drawableIds) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.titles = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(titleIds)));
        this.drawable = context.getResources().obtainTypedArray(drawableIds);
        final DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) view = inflater.inflate(R.layout.item_card_menu, parent, false);
        final MenuViewHolder holder = new MenuViewHolder(view);
        holder.imageViewLogo.setImageDrawable(drawable.getDrawable(position));
        holder.textViewTitle.setText(titles.get(position));
        holder.textViewTitle.setSelected(true);
        return view;
    }
}