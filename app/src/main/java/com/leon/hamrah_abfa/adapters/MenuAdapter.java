package com.leon.hamrah_abfa.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.MenuHolder;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {
    private final ArrayList<String> titles = new ArrayList<>();
    private final TypedArray drawable;
    private final LayoutInflater inflater;

    public MenuAdapter(Context context, ArrayList<String> titles, TypedArray drawable) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.titles.addAll(titles);
        this.drawable = drawable;
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
        if (view == null) view = inflater.inflate(R.layout.item_card_layout, parent, false);
        final MenuHolder holder = new MenuHolder(view);
        holder.imageViewLogo.setImageDrawable(drawable.getDrawable(position));
        holder.textViewTitle.setText(titles.get(position));
        return view;
    }
}