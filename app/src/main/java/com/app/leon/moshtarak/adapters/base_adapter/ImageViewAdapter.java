package com.app.leon.moshtarak.adapters.base_adapter;

import static com.leon.toast.RTLToast.warning;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.app.leon.moshtarak.adapters.holders.ImageViewHolder;
import com.app.leon.moshtarak.R;

import java.util.ArrayList;

public class ImageViewAdapter extends BaseAdapter {
    private final ArrayList<Bitmap> bitmaps;
    private final LayoutInflater inflater;
    private final Context context;
    private long lastClickTime = 0;

    public ImageViewAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bitmaps = new ArrayList<>();
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_image, parent, false);
        }
        final ImageViewHolder holder = new ImageViewHolder(view);
        if (bitmaps.isEmpty() || position == bitmaps.size()) {
            holder.imageView.setImageResource(R.drawable.ic_image_raw);
            holder.imageViewDelete.setVisibility(View.GONE);
        } else {
            holder.imageView.setImageBitmap(bitmaps.get(position));
            holder.imageViewDelete.setVisibility(View.VISIBLE);
            holder.imageViewDelete.setOnClickListener(v -> {
                if (SystemClock.elapsedRealtime() - lastClickTime < 2000) {
                    bitmaps.remove(position);
                    notifyDataSetChanged();
                }
                warning(context, context.getString(R.string.delete_by_press_again)).show();
                lastClickTime = SystemClock.elapsedRealtime();
            });
        }
        return view;
    }

    public void addItem(Bitmap bitmap) {
        bitmaps.add(bitmap);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Math.min(bitmaps.size() + 1, 5);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }
}
