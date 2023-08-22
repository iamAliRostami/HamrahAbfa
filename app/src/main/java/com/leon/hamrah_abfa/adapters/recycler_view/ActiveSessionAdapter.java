package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.ActiveSessionViewHolder;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ActiveSessionViewModel;

import java.util.ArrayList;

public class ActiveSessionAdapter extends RecyclerView.Adapter<ActiveSessionViewHolder> {
    private final ArrayList<ActiveSessionViewModel> activeSessionViewModels = new ArrayList<>();
    private final LayoutInflater inflater;

    public ActiveSessionAdapter(Context context, ArrayList<ActiveSessionViewModel> activeSessionViewModels) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activeSessionViewModels.addAll(activeSessionViewModels);
    }

    @NonNull
    @Override
    public ActiveSessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActiveSessionViewHolder(inflater.inflate(R.layout.item_active_session, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveSessionViewHolder holder, int position) {
        holder.textViewIP.setText(activeSessionViewModels.get(position).ip);
        holder.textViewLastLogin.setText(String.format("%s - %s", activeSessionViewModels.get(position).insertDayJalali,
                activeSessionViewModels.get(position).insertTime));
        holder.textViewDevice.setText(activeSessionViewModels.get(position).deviceInfo);
        holder.textViewValidate.setText(activeSessionViewModels.get(position).isValidated ?
                inflater.getContext().getString(R.string.successful) :
                inflater.getContext().getString(R.string.unsuccessful));
    }

    @Override
    public int getItemCount() {
        return activeSessionViewModels.size();
    }
}
