package com.leon.hamrah_abfa.adapters;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.NICKNAME;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.OWNER;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.leon.hamrah_abfa.fragments.ui.cards.CardEmptyFragment;
import com.leon.hamrah_abfa.fragments.ui.cards.CardFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class CardPagerAdapter2 extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

    public CardPagerAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        final String billId = getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue());
        final String nickname = getApplicationComponent().SharedPreferenceModel().getStringData(NICKNAME.getValue());
        final String owner = getApplicationComponent().SharedPreferenceModel().getStringData(OWNER.getValue());
        ArrayList<String> billIds = new ArrayList<>();
        ArrayList<String> owners = new ArrayList<>();
        ArrayList<String> nicknames = new ArrayList<>();
        if (!(billId.isEmpty() || nickname.isEmpty() || owner.isEmpty())) {
            billIds.addAll(Arrays.asList(billId.split(",")));
            nicknames.addAll(Arrays.asList(nickname.split(",")));
            owners.addAll(Arrays.asList(owner.split(",")));
        }

        for (int i = 0; i < Math.min(Math.min(billIds.size(), nicknames.size()), owners.size()); i++) {
            addFragment(CardFragment.newInstance(billIds.get(i), nicknames.get(i), owners.get(i)));
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
        final String billId = getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue());
        final String nickname = getApplicationComponent().SharedPreferenceModel().getStringData(NICKNAME.getValue());
        final String owner = getApplicationComponent().SharedPreferenceModel().getStringData(OWNER.getValue());
        if (!(billId.isEmpty() || nickname.isEmpty() || owner.isEmpty())) {
            addFragment(CardFragment.newInstance(billId.split(",")[getItemCount() - 1],
                            nickname.split(",")[getItemCount() - 1], owner.split(",")[getItemCount() - 1]),
                    getItemCount() - 1);
        }
        notifyDataSetChanged();
    }
}
