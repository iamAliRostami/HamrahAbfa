package com.leon.hamrah_abfa.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.ServiceMainHolder;

import java.util.ArrayList;
import java.util.Arrays;

public class ServicesMainAdapterOld extends BaseAdapter {
    private final ArrayList<String> titles;
    private final TypedArray drawable;
    private final LayoutInflater inflater;

    public ServicesMainAdapterOld(Context context, int titleIds, int drawableIds) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.titles = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(titleIds)));
        this.drawable = context.getResources().obtainTypedArray(drawableIds);
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
        if (view == null) view = inflater.inflate(R.layout.item_service_main, parent, false);
        final ServiceMainHolder holder = new ServiceMainHolder(view);
        holder.imageViewLogo.setImageDrawable(drawable.getDrawable(position));
        holder.textViewTitle.setText(titles.get(position));
        return view;
    }
}