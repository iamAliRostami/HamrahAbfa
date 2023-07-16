package com.leon.hamrah_abfa.adapters.fragment_state_adapter;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ALIAS;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEBT;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEFAULT_BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ID;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.leon.hamrah_abfa.fragments.cards.BillCardViewModel;
import com.leon.hamrah_abfa.fragments.cards.CardEmptyFragment;
import com.leon.hamrah_abfa.fragments.cards.CardFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class CardPagerAdapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

    private final ArrayList<BillCardViewModel> bills = new ArrayList<>();

    public CardPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        String ids = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ID.getValue());
        ArrayList<String> id = new ArrayList<>(Arrays.asList(ids.split(",")));
        ArrayList<String> billId = new ArrayList<>(Arrays.asList(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue()).split(",")));
        ArrayList<String> alias = new ArrayList<>(Arrays.asList(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ALIAS.getValue()).split(",")));
        ArrayList<String> debt = new ArrayList<>(Arrays.asList(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(DEBT.getValue()).split(",")));

        if (ids.length() > 0) {
            for (int i = 0; i < id.size(); i++) {
                bills.add(new BillCardViewModel(id.get(i), billId.get(i), alias.get(i), debt.get(i)));
            }
            bills.add(bills.get(getInstance().getApplicationComponent().SharedPreferenceModel().getIntData(DEFAULT_BILL_ID.getValue()) - 1));
        }
        for (int i = 0; i < bills.size() - 1; i++) {
            addFragment(CardFragment.newInstance(bills.get(i)));
        }
        addFragment(CardEmptyFragment.newInstance());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    public void addFragment(Fragment fragment, int position) {
        fragmentList.add(position, fragment);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update() {
        final String id = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ID.getValue());
        final String billId = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue());
        final String alias = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ALIAS.getValue());
        final String debt = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(DEBT.getValue());

        bills.add(new BillCardViewModel(id.split(",")[getItemCount() - 1], billId.split(",")[getItemCount() - 1],
                alias.split(",")[getItemCount() - 1], debt.split(",")[getItemCount() - 1]));
        addFragment(CardFragment.newInstance(bills.get(getItemCount() - 1)));
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return bills.isEmpty();
    }

    public String getCurrentId(int position) {
        return bills.get(position).getId();
    }
    public String getCurrentBillId(int position) {
        return bills.get(position).getBillId();
    }
}
