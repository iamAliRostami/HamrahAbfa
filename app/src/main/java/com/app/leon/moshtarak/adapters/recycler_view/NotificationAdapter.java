package com.app.leon.moshtarak.adapters.recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.adapters.holders.NotificationViewHolder;
import com.app.leon.moshtarak.fragments.notifications.NotificationsViewModel;
import com.app.leon.moshtarak.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    private final LayoutInflater inflater;
    private final TypedArray icons;
    private final ArrayList<NotificationsViewModel> notifications;

    public NotificationAdapter(Context context, ArrayList<NotificationsViewModel> notifications) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icons = context.getResources().obtainTypedArray(R.array.notification_icons);
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(inflater.inflate(R.layout.item_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.imageView.setImageDrawable(icons.getDrawable(notifications.get(position).getType() - 1));
        holder.textViewDate.setText(notifications.get(position).getInsertDateTime());
//        holder.textViewDate.setText(notifications.get(position).getInsertDateTime()
//                .replace("-", "/").replace("T", " - "));
        holder.textViewTitle.setText(notifications.get(position).getTitle());
        holder.textViewSummary.setText(notifications.get(position).getMessage().substring(0,
                Math.min(40, notifications.get(position).getMessage().length())).concat(inflater.getContext().getString(R.string.dots)));
        holder.relativeLayout.setBackgroundResource(notifications.get(position).getSeenDateTime() == null ?
                R.drawable.background_white_blue : R.drawable.background_seen);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void updateNotificationSeen(int position) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy MM dd HH:mm:ss:SSS");
        notifications.get(position).setSeenDateTime(dateFormatter.format(new Date(Calendar.getInstance().getTimeInMillis())));
        notifyItemChanged(position);
    }
}