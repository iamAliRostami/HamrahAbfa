package com.leon.hamrah_abfa.activities;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.ViewPagerAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityNotificationsBinding;
import com.leon.hamrah_abfa.fragments.notifications.NewsFragment;
import com.leon.hamrah_abfa.fragments.notifications.NotificationFragment;

public class NotificationsActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    private ActivityNotificationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initialize() {
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeViewPager();
    }

    private void initializeViewPager() {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        adapter.addFragment(NotificationFragment.newInstance());
        adapter.addFragment(NewsFragment.newInstance());
        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.services)).setIcon(android.R.drawable.ic_popup_reminder));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.news)).setIcon(android.R.drawable.ic_dialog_email));

        BadgeDrawable badgeDrawable = binding.tabLayout.getTabAt(0).getOrCreateBadge();
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(1);

        badgeDrawable = binding.tabLayout.getTabAt(1).getOrCreateBadge();
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(88);

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });
        binding.tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        binding.viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}