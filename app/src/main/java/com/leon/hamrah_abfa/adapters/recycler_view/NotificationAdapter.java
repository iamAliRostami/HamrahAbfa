package com.leon.hamrah_abfa.adapters.recycler_view;

import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.NotificationViewHolder;
import com.leon.hamrah_abfa.tables.Notification;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    private final LayoutInflater inflater;
    private ArrayList<Notification> notifications;
    private final TypedArray icons;
    private final String billId;


    public NotificationAdapter(Context context, String billId) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.icons = context.getResources().obtainTypedArray(R.array.notification_icons);
        this.billId = billId;
        setNotifications();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(inflater.inflate(R.layout.item_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.imageView.setImageDrawable(icons.getDrawable(notifications.get(position).category-1));
        holder.textViewDate.setText(notifications.get(position).date);
        holder.textViewTitle.setText(notifications.get(position).title);
        holder.textViewSummary.setText(notifications.get(position).summary);
        holder.relativeLayout.setBackgroundResource(notifications.get(position).seen ?
                R.drawable.background_seen : R.drawable.background_white_blue);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setNotifications() {
        notifications = new ArrayList<>(getApplicationComponent().MyDatabase().notificationDao().getNotificationsByBillId(billId));
    }

    public Notification getNotification(int position) {
        return notifications.get(position);
    }

    public void updateNotification(int position) {
        notifications.get(position).seen = true;
        notifyItemChanged(position);
    }
}
