package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.FAQViewHolder;

import java.util.ArrayList;
import java.util.Arrays;

public class FAQAdapter extends RecyclerView.Adapter<FAQViewHolder> {
    private final ArrayList<String> questions;
    private final ArrayList<String> answers;
    public final LayoutInflater inflater;
    private Integer selectedServices = null;

    public FAQAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.questions = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.faq_questions)));
        this.answers = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.faq_answers)));
    }

    @NonNull
    @Override
    public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (selectedServices != null && selectedServices == viewType) {
            return new FAQViewHolder(inflater.inflate(R.layout.item_faq_collapsed, parent, false));
        } else {
            return new FAQViewHolder(inflater.inflate(R.layout.item_faq, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull FAQViewHolder holder, int position) {
        holder.textViewQuestion.setText(questions.get(position));
        holder.textViewQuestion.setSelected(true);
        if (selectedServices != null && selectedServices == position)
            holder.textViewAnswer.setText(answers.get(position));
    }

    @Override
    public int getItemCount() {
        return Math.min(questions.size(), answers.size());
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
}
