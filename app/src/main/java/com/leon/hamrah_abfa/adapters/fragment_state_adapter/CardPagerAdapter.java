package com.leon.hamrah_abfa.adapters.fragment_state_adapter;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ALIAS;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEFAULT_BILL_ID;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.leon.hamrah_abfa.fragments.ui.cards.CardEmptyFragment;
import com.leon.hamrah_abfa.fragments.ui.cards.CardFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class CardPagerAdapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

    private final ArrayList<String> billIds = new ArrayList<>();

    public CardPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        final String billId = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue());
        final String nickname = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ALIAS.getValue());
        ArrayList<String> nicknames = new ArrayList<>();
        if (!(billId.isEmpty() || nickname.isEmpty())) {
            nicknames.addAll(Arrays.asList(nickname.split(",")));
            billIds.addAll(Arrays.asList(billId.split(",")));
            billIds.add(billIds.get(getInstance().getApplicationComponent().SharedPreferenceModel().getIntData(DEFAULT_BILL_ID.getValue()) - 1));
        } else {
            billIds.add("");
        }

        for (int i = 0; i < Math.min(billIds.size(), nicknames.size()); i++) {
            addFragment(CardFragment.newInstance(billIds.get(i), nicknames.get(i)));
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
        final String billId = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue());
        final String nickname = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ALIAS.getValue());
        if (!(billId.isEmpty() || nickname.isEmpty())) {
            addFragment(CardFragment.newInstance(billId.split(",")[getItemCount() - 1],
                    nickname.split(",")[getItemCount() - 1]), getItemCount() - 1);
            billIds.add(getItemCount() - 1, billId.split(",")[getItemCount() - 1]);
        }
        notifyDataSetChanged();
    }

    public String getCurrentBillId(int position) {
        return billIds.get(position);
    }
}
