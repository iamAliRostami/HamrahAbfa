package com.leon.hamrah_abfa.adapters.recycler_view;

import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.NewsViewHolder;
import com.leon.hamrah_abfa.tables.News;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private final LayoutInflater inflater;
    private ArrayList<News> news;
    private final TypedArray icons;
    private final String billId;


    public NewsAdapter(Context context, String billId) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icons = context.getResources().obtainTypedArray(R.array.notification_icons);
        this.billId = billId;
        setNotifications();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(inflater.inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.imageView.setImageDrawable(icons.getDrawable(news.get(position).category - 1));
        holder.textViewDate.setText(news.get(position).date);
        holder.textViewTitle.setText(news.get(position).title);
        holder.textViewSummary.setText(news.get(position).summary);
        holder.relativeLayout.setBackgroundResource(news.get(position).seen ?
                R.drawable.background_seen : R.drawable.background_white_blue);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void setNotifications() {
        news = new ArrayList<>(getApplicationComponent().MyDatabase().newsDao().getNewsBillId(billId));
    }

    public News getNews(int position) {
        return news.get(position);
    }

    public void updateNews(int position) {
        news.get(position).seen = true;
        notifyItemChanged(position);
    }
}
