package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.ActiveSessionViewHolder;
import com.leon.hamrah_abfa.tables.ActiveSession;

import java.util.ArrayList;

public class ActiveSessionAdapter extends RecyclerView.Adapter<ActiveSessionViewHolder> {
    private final ArrayList<ActiveSession> activeSessions = new ArrayList<>();
    public final LayoutInflater inflater;

    public ActiveSessionAdapter(Context context, ArrayList<ActiveSession> activeSessions) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activeSessions.addAll(activeSessions);
        Log.e("size", String.valueOf(this.activeSessions.size()));
    }

    @NonNull
    @Override
    public ActiveSessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActiveSessionViewHolder(inflater.inflate(R.layout.item_active_session, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveSessionViewHolder holder, int position) {
        Log.e("position",String.valueOf(position));
        holder.textViewIP.setText(activeSessions.get(position).getIp());
        holder.textViewMobile.setText(activeSessions.get(position).getMobile());
        holder.textViewLastLogin.setText(activeSessions.get(position).getLastLogin());
        holder.textViewDevice.setText(activeSessions.get(position).getDeviceName());
    }

    @Override
    public int getItemCount() {
        return activeSessions.size();
    }
}
