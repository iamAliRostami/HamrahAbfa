package com.app.leon.moshtarak.adapters.fragment_state_adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.leon.moshtarak.fragments.ui.help.HelpViewPagerFragment;
import com.app.leon.moshtarak.R;

public class WelcomePagerAdapter extends FragmentStateAdapter {
    private final String[] title;
    private final String[] content;

    private final int[] rawSrc = new int[]{
            R.raw.online_assistant,
            R.raw.bill_payment,
            R.raw.data_dnalysis,
            R.raw.citizen,
            R.raw.repairman_robot
    };

    public WelcomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        title = fragmentActivity.getResources().getStringArray(R.array.welcome_item_titles);
        content = fragmentActivity.getResources().getStringArray(R.array.welcome_item_contents);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return HelpViewPagerFragment.newInstance(position, title[position], content[position],
                rawSrc[position]);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
