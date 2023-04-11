package com.leon.hamrah_abfa.adapters;

import android.content.res.TypedArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.fragments.HelpViewPagerFragment;

public class WelcomePagerAdapter extends FragmentStateAdapter {
    private final TypedArray bgColors;
    private final TypedArray icons;
    private final String[] title;
    private final String[] content;

    public WelcomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        icons = fragmentActivity.getResources().obtainTypedArray(R.array.welcome_icons);
        title = fragmentActivity.getResources().getStringArray(R.array.welcome_item_titles);
        bgColors = fragmentActivity.getResources().obtainTypedArray(R.array.welcome_bg_colors);
        content = fragmentActivity.getResources().getStringArray(R.array.welcome_item_contents);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return HelpViewPagerFragment.newInstance(position, bgColors.getColor(position, 0),
                icons.getResourceId(position, 0), title[position], content[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public int getBgColors(int position) {
        return bgColors.getColor(position, 0);
    }
}
