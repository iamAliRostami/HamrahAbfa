package com.app.leon.moshtarak.adapters.recycler_view;

import static com.app.leon.moshtarak.enums.SharedReferenceKeys.ALIAS;
import static com.app.leon.moshtarak.enums.SharedReferenceKeys.BILL_ID;
import static com.app.leon.moshtarak.enums.SharedReferenceKeys.DEADLINE;
import static com.app.leon.moshtarak.enums.SharedReferenceKeys.DEBT;
import static com.app.leon.moshtarak.enums.SharedReferenceKeys.IS_PAYED;
import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.adapters.holders.PayBillViewHolder;
import com.app.leon.moshtarak.fragments.pay_bill.PayBillViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class PayBillAdapter extends RecyclerView.Adapter<PayBillViewHolder> implements View.OnClickListener {
    private final LayoutInflater inflater;
    private final ArrayList<PayBillViewModel> payBills = new ArrayList<>();
    private final ArrayList<String> selectedBills = new ArrayList<>();
    private int selectedNumber;
    private long selectedPrice;

    public PayBillAdapter(Context context) {
        ArrayList<String> billIds = new ArrayList<>();
        ArrayList<String> nicknames = new ArrayList<>();
        ArrayList<String> debts = new ArrayList<>();
        ArrayList<String> deadlines = new ArrayList<>();
        String nickname = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ALIAS.getValue());
        String billId = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue());
        String debt = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(DEBT.getValue());
        String deadline = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(DEADLINE.getValue());
        if (!billId.isEmpty() && !nickname.isEmpty() && !debt.isEmpty()) {
            billIds.addAll(Arrays.asList(billId.split(",")));
            nicknames.addAll(Arrays.asList(nickname.split(",")));
            debts.addAll(Arrays.asList(debt.split(",")));
            deadlines.addAll(Arrays.asList(deadline.split(",")));
        }
        //TODO
        for (int i = 0; i < billIds.size(); i++) {
            if (!getInstance().getApplicationComponent().SharedPreferenceModel().getBoolData(IS_PAYED.getValue().concat(billIds.get(i))))
                payBills.add(new PayBillViewModel(nicknames.get(i), debts.get(i), deadlines.get(i), billIds.get(i)));
        }
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public PayBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PayBillViewHolder(inflater.inflate(R.layout.item_pay_bill, parent, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull PayBillViewHolder holder, int position) {
        if (payBills.get(position).selected) {
            holder.relativeLayoutContent.setBackgroundResource(R.drawable.background_pay_bill_selected);
            holder.imageView.setImageResource(R.drawable.ic_minus);
        } else {
            holder.relativeLayoutContent.setBackgroundResource(R.drawable.background_pay_bill);
            holder.imageView.setImageResource(R.drawable.ic_plus);
        }
        holder.textViewNickname.setText(payBills.get(position).nickName);
        holder.textViewDeadline.setText(payBills.get(position).deadline);
        holder.textViewPrice.setText(String.format("%,d ریال", Long.parseLong(payBills.get(position).debt)));
        holder.relativeLayoutContent.setOnClickListener(this);
        holder.textViewNickname.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getItemCount() {
        return payBills.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateSelectedBill(int position) {
        payBills.get(position).selected = !payBills.get(position).selected;
        if (payBills.get(position).selected) {
            selectedNumber++;
            selectedPrice += Long.parseLong(payBills.get(position).debt);
            selectedBills.add(payBills.get(position).billId);
        } else {
            selectedNumber--;
            selectedPrice -= Long.parseLong(payBills.get(position).debt);
            selectedBills.remove(payBills.get(position).billId);
        }
        notifyItemChanged(position);
    }

    public int getSelectedNumber() {
        return selectedNumber;
    }

    public long getSelectedPrice() {
        return selectedPrice;
    }

    public boolean isPayed(int position) {
        return payBills.get(position).isPayed;
    }

    public ArrayList<String> getSelectedBills() {
        return selectedBills;
    }
}
