package com.app.leon.moshtarak.adapters.recycler_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.adapters.holders.NewsViewHolder;
import com.app.leon.moshtarak.fragments.notifications.NewsViewModel;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    private final LayoutInflater inflater;
    private final TypedArray icons;
    private final ArrayList<NewsViewModel> news = new ArrayList<>();


    public NewsAdapter(Context context, ArrayList<NewsViewModel> news) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icons = context.getResources().obtainTypedArray(R.array.notification_icons);
        this.news.addAll(news);
//        setNotifications();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(inflater.inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.imageView.setImageDrawable(icons.getDrawable(news.get(position).getType() - 1));
        holder.textViewDate.setText(news.get(position).getDayJalali());
        holder.textViewTitle.setText(news.get(position).getTitle());

        holder.textViewSummary.setText(news.get(position).getDescription().substring(0,
                Math.min(40, news.get(position).getDescription().length())).concat(inflater.getContext().getString(R.string.dots)));
//        holder.textViewSummary.setText(news.get(position).getDescription().substring(50)
//                .concat(inflater.getContext().getString(R.string.dots)));
//        holder.relativeLayout.setBackgroundResource(news.get(position).seen ?
//                R.drawable.background_seen : R.drawable.background_white_blue);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

//    public void setNotifications() {
//        news = new ArrayList<>(getInstance().getApplicationComponent().MyDatabase().newsDao().getNewsBillId(billId));
//    }

    public NewsViewModel getNews(int position) {
        return news.get(position);
    }

//    public void updateNews(int position) {
////        news.get(position).seen = true;
//        notifyItemChanged(position);
//    }
}
