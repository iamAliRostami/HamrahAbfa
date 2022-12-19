package com.leon.hamrah_abfa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.MenuHolder;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {
    private final ArrayList<String> titles = new ArrayList<>();
    private final ArrayList<Integer> logos = new ArrayList<>();
    private final LayoutInflater inflater;
    private final Context context;

    public MenuAdapter(Context context, ArrayList<String> titles, ArrayList<Integer> logos) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.titles.addAll(titles);
        this.logos.addAll(logos);
        this.context = context;
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
//TODO
//        if (view == null) view = inflater.inflate(R.layout.card_layout, null);
        if (view == null) view = inflater.inflate(R.layout.card_layout, parent, false);
        final MenuHolder holder = new MenuHolder(view);
        holder.imageViewLogo.setImageResource(logos.get(position));
        holder.textViewTitle.setText(titles.get(position));
        return view;
    }
}