package com.app.leon.moshtarak.adapters.recycler_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.adapters.holders.TileViewHolder;
import com.app.leon.moshtarak.R;

import java.util.ArrayList;
import java.util.Arrays;

public class TileAdapter extends RecyclerView.Adapter<TileViewHolder> {
    private final ArrayList<String> titles;
    private final TypedArray drawable;
    private final LayoutInflater inflater;

    public TileAdapter(Context context, int titleIds, int drawableIds) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        titles = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(titleIds)));
        drawable = context.getResources().obtainTypedArray(drawableIds);
    }

    @NonNull
    @Override
    public TileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TileViewHolder(inflater.inflate(R.layout.item_tile, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TileViewHolder holder, int position) {
        holder.imageViewLogo.setImageDrawable(drawable.getDrawable(position));
        holder.textViewTitle.setText(titles.get(position));
        holder.textViewTitle.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
}
