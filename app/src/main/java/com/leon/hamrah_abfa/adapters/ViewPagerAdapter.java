package com.leon.hamrah_abfa.adapters;

import static com.leon.hamrah_abfa.helpers.MyApplication.getAppContext;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.fragments.ViewPagerFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final int[] bgColors = {
            ContextCompat.getColor(getAppContext(), android.R.color.holo_green_dark),
            ContextCompat.getColor(getAppContext(), android.R.color.holo_green_dark),
            ContextCompat.getColor(getAppContext(), android.R.color.holo_green_dark),
            ContextCompat.getColor(getAppContext(), android.R.color.holo_green_dark)
    };
    private final int[] logos = {
            R.drawable.img_splash,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round
    };

    private final String[] title = new String[bgColors.length];
    private final String[] content = new String[bgColors.length];

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        title[position] = "Welcome, this is Page: " + position + 1;
        content[position] = "This is Page: " + position + 1;
        return ViewPagerFragment.newInstance(position, bgColors[position], logos[position],
                title[position], content[position]);
    }

    @Override
    public int getItemCount() {
        return bgColors.length;
    }

    public int[] getBgColors() {
        return bgColors;
    }
}
