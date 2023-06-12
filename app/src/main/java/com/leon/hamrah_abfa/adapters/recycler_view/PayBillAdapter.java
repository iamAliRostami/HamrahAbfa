package com.leon.hamrah_abfa.adapters.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.holders.PayBillViewHolder;
import com.leon.hamrah_abfa.fragments.pay_bill.PayBillViewModel;

import java.util.ArrayList;

public class PayBillAdapter extends RecyclerView.Adapter<PayBillViewHolder> implements View.OnClickListener {

    private final LayoutInflater inflater;
    private final ArrayList<PayBillViewModel> payBills;
    private int selectedNumber;
    private int selectedPrice;

    public PayBillAdapter(Context context, ArrayList<PayBillViewModel> payBills) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.payBills = new ArrayList<>(payBills);
    }

    @NonNull
    @Override
    public PayBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PayBillViewHolder(inflater.inflate(R.layout.item_pay_bill, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PayBillViewHolder holder, int position) {
        if (payBills.get(position).selected) {
            holder.linearLayoutContent.setBackgroundResource(R.drawable.background_pay_bill_selected);
        } else {
            holder.linearLayoutContent.setBackgroundResource(R.drawable.background_pay_bill);
        }
        holder.textViewNickname.setChecked(payBills.get(position).selected);
        holder.textViewNickname.setText(payBills.get(position).nickName);

        holder.textViewDeadline.setText(payBills.get(position).deadline);
        holder.textViewPrice.setText(payBills.get(position).debt);

        holder.linearLayoutContent.setOnClickListener(this);
        holder.textViewNickname.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getItemCount() {
        return payBills.size();
    }

    //    @Override
//    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
//    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateSelectedBill(int position) {
        payBills.get(position).selected = !payBills.get(position).selected;
        if (payBills.get(position).selected) {
            selectedNumber++;
            selectedPrice += Integer.parseInt(payBills.get(position).debt.replace(" ریال", ""));
        } else {
            selectedNumber--;
            selectedPrice -= Integer.parseInt(payBills.get(position).debt.replace(" ریال", ""));
        }
        notifyItemChanged(position);
    }

    public int getSelectedNumber() {
        return selectedNumber;
    }

    public int getSelectedPrice() {
        return selectedPrice;
    }
}
