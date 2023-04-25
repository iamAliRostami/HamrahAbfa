package com.leon.hamrah_abfa.adapters;

import static com.leon.toast.RTLToast.warning;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.ImageViewHolder;

import java.util.ArrayList;

public class ImageViewAdapter extends BaseAdapter {
    final ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private final LayoutInflater inflater;
    private long lastClickTime = 0;
    private final Context context;

    public ImageViewAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_image, null);
        }
        final ImageViewHolder holder = new ImageViewHolder(view);
        Log.e("position 1", String.valueOf(position));
        Log.e("size 1", String.valueOf(bitmaps.size()));
        if (position < bitmaps.size()) {
            Log.e("position 2", String.valueOf(position));
            holder.imageView.setImageBitmap(bitmaps.get(position));
            holder.imageViewDelete.setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - lastClickTime < 2000) {
                    bitmaps.remove(position);
                    notifyDataSetChanged();
                }
                warning(context, context.getString(R.string.delete_by_press_again)).show();
                lastClickTime = SystemClock.elapsedRealtime();
            });
        } else {
            Log.e("position 3", String.valueOf(position));
//            holder.imageViewDelete.setVisibility(View.GONE);
        }
        return view;
    }

    public void addItem(Bitmap bitmap) {
        bitmaps.add(bitmap);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bitmaps.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return bitmaps.size() + 1;
    }
}
