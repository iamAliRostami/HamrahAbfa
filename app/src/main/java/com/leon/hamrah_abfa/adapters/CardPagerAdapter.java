package com.leon.hamrah_abfa.adapters;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.leon.hamrah_abfa.fragments.CardEmptyFragment;
import com.leon.hamrah_abfa.fragments.CardFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class CardPagerAdapter extends FragmentStateAdapter {
    //    StringBuilder sb = new StringBuilder();
//for (int i = 0; i < playlists.length; i++) {
//        sb.append(playlists[i]).append(",");
//    }
//prefsEditor.putString(PLAYLISTS, sb.toString());
    private final ArrayList<String> billIds = new ArrayList<>();

    public CardPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        final String s = getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue());
        if (!s.isEmpty())
            billIds.addAll(Arrays.asList(s.split(",")));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == billIds.size())
            return CardEmptyFragment.newInstance();
        return CardFragment.newInstance("", "");
    }

    @Override
    public int getItemCount() {
        return billIds.size() + 1;
    }
}
