package com.leon.hamrah_abfa.adapters;

import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.leon.hamrah_abfa.enums.SharedReferenceKeys;
import com.leon.hamrah_abfa.fragments.CardEmptyFragment;
import com.leon.hamrah_abfa.fragments.CardFragment;

public class CardPagerAdapter extends FragmentStateAdapter {
    //    StringBuilder sb = new StringBuilder();
//for (int i = 0; i < playlists.length; i++) {
//        sb.append(playlists[i]).append(",");
//    }
//prefsEditor.putString(PLAYLISTS, sb.toString());
    private final String[] billIds = getApplicationComponent().SharedPreferenceModel()
            .getStringData(SharedReferenceKeys.BILL_ID.getValue()).split(",");

    public CardPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position > billIds.length)
            return CardEmptyFragment.newInstance();
        return CardFragment.newInstance("", "");
    }

    @Override
    public int getItemCount() {
        return billIds.length + 1;
    }
}
