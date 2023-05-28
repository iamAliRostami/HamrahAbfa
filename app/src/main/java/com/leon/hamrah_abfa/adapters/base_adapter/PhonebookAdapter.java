package com.leon.hamrah_abfa.adapters.base_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.PhonebookViewHolder;
import com.leon.hamrah_abfa.fragments.contact_us.PhonebookViewModel;

import java.util.ArrayList;

public class PhonebookAdapter extends BaseAdapter {
    private final ArrayList<PhonebookViewModel> phoneBook;
    private final LayoutInflater inflater;

    public PhonebookAdapter(Context context, ArrayList<PhonebookViewModel> phoneBook) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.phoneBook = new ArrayList<>(phoneBook);
    }

    @Override
    public int getCount() {
        return phoneBook.size();
    }

    @Override
    public Object getItem(int position) {
        return phoneBook.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) view = inflater.inflate(R.layout.item_phone, parent, false);
        final PhonebookViewHolder holder = new PhonebookViewHolder(view);
        holder.textViewTitle.setText(phoneBook.get(position).getTitle());
        holder.textViewTitle.setSelected(true);

        holder.textViewPhoneNumber.setText(phoneBook.get(position).getPhoneNumber());
        holder.textViewPhoneNumber.setSelected(true);
        return view;
    }
}