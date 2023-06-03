package com.leon.hamrah_abfa.adapters.recycler_view;

import static com.leon.hamrah_abfa.enums.FragmentTags.BRANCH_LOCATION;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.BranchViewHolder;
import com.leon.hamrah_abfa.fragments.contact_us.BranchViewModel;
import com.leon.hamrah_abfa.fragments.contact_us.ContactBranchLocationFragment;

import java.util.ArrayList;

public class BranchAdapter extends RecyclerView.Adapter<BranchViewHolder> {
    private final LayoutInflater inflater;
    private final ArrayList<BranchViewModel> branches;
    private Integer selectedServices = null;

    public BranchAdapter(Context context, ArrayList<BranchViewModel> branches) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.branches = new ArrayList<>(branches);
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (selectedServices != null && selectedServices == viewType) {
            return new BranchViewHolder(inflater.inflate(R.layout.item_branch_collapsed, parent, false));
        } else {
            return new BranchViewHolder(inflater.inflate(R.layout.item_branch, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder holder, int position) {
        holder.textViewName.setText(branches.get(position).getName());
        holder.textViewName.setSelected(true);
        holder.textViewName.setOnClickListener(v -> updateSelectedService(position));
        holder.imageViewArrow.setOnClickListener(v -> updateSelectedService(position));
        if (selectedServices != null && selectedServices == position) {
            holder.textViewManager.setText(branches.get(position).getManager());
            holder.textViewFax.setText(branches.get(position).getFax());
            holder.textViewPhone1.setText(branches.get(position).getPhone1());
            holder.textViewPostal.setText(branches.get(position).getPostal());
            holder.textViewAddress.setText(branches.get(position).getAddress());
            holder.textViewZone.setText(branches.get(position).getZone());
            holder.textViewFinancialCode.setText(branches.get(position).getFinancialCode());
            holder.imageViewAddress.setOnClickListener(v -> {
                showFragmentDialogOnce(inflater.getContext(), BRANCH_LOCATION.getValue(),
                        ContactBranchLocationFragment.newInstance(branches.get(position).getPoint()));
            });
        }
    }

    @Override
    public int getItemCount() {
        return branches.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateSelectedService(int position) {
        if (selectedServices == null) {
            selectedServices = position;
        } else if (selectedServices == position) {
            selectedServices = null;
        } else {
            final int temp = selectedServices;
            selectedServices = position;
            notifyItemChanged(temp);
        }
        notifyItemChanged(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<BranchViewModel> branches) {
        this.branches.clear();
        this.branches.addAll(branches);
        notifyDataSetChanged();
    }
}
