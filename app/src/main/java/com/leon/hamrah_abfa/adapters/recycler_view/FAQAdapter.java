package com.leon.hamrah_abfa.adapters.recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.FAQViewHolder;
import com.leon.hamrah_abfa.fragments.contact_us.ContactFAQViewModel;

import java.util.ArrayList;

public class FAQAdapter extends RecyclerView.Adapter<FAQViewHolder> {
    private final ArrayList<ContactFAQViewModel> faq;
    public final LayoutInflater inflater;
    private Integer selectedFAQ = null;

    public FAQAdapter(Context context, ArrayList<ContactFAQViewModel> faq) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.faq = new ArrayList<>(faq);
    }

    @NonNull
    @Override
    public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FAQViewHolder(inflater.inflate((selectedFAQ != null && selectedFAQ == viewType) ?
                R.layout.item_faq_collapsed : R.layout.item_faq, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FAQViewHolder holder, int position) {
        holder.textViewQuestion.setText(faq.get(position).q);
        holder.textViewQuestion.setSelected(true);
        if (selectedFAQ != null && selectedFAQ == position)
            holder.textViewAnswer.setText(faq.get(position).a);
    }

    @Override
    public int getItemCount() {
        return faq.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateSelectedService(int position) {
        if (selectedFAQ == null) {
            selectedFAQ = position;
        } else if (selectedFAQ == position) {
            selectedFAQ = null;
        } else {
            final int temp = selectedFAQ;
            selectedFAQ = position;
            notifyItemChanged(temp);
        }
        notifyItemChanged(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<ContactFAQViewModel> faq) {
        this.faq.clear();
        this.faq.addAll(faq);
        notifyDataSetChanged();
    }
}