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

import java.util.ArrayList;
import java.util.Arrays;

public class CardPagerAdapter2 extends FragmentStateAdapter {
    private final ArrayList<String> billIds = new ArrayList<>();
    private final ArrayList<String> nicknames = new ArrayList<>();
    private final ArrayList<String> owners = new ArrayList<>();

    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

    public CardPagerAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        update();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        if (position == getItemCount() - 1)
//            return CardEmptyFragment.newInstance();
//        return CardFragment.newInstance(billIds.get(position), nicknames.get(position), owners.get(position));
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void update() {
        final String billId = getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue());
        final String nickname = getApplicationComponent().SharedPreferenceModel().getStringData(NICKNAME.getValue());
        final String owner = getApplicationComponent().SharedPreferenceModel().getStringData(OWNER.getValue());
        if (!(billId.isEmpty() || nickname.isEmpty() || owner.isEmpty())) {
            billIds.addAll(Arrays.asList(billId.split(",")));
            nicknames.addAll(Arrays.asList(nickname.split(",")));
            owners.addAll(Arrays.asList(owner.split(",")));
        }
        notifyDataSetChanged();
    }
}
