package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.fragment_state_adapter.ViewPagerAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityUsageHistoryBinding;
import com.leon.hamrah_abfa.fragments.checkout.CheckoutBillFragment;
import com.leon.hamrah_abfa.fragments.checkout.CheckoutPaymentFragment;
import com.leon.hamrah_abfa.fragments.usage_history.UsageHistoryFailedFragment;
import com.leon.hamrah_abfa.fragments.usage_history.UsageHistorySuccessfulFragment;

public class UsageHistoryActivity extends BaseActivity implements TabLayout.OnTabSelectedListener{
    private ActivityUsageHistoryBinding binding;
    private String billId;

    @Override
    protected void initialize() {
        binding = ActivityUsageHistoryBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            billId = getIntent().getExtras().getString(BILL_ID.getValue());
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        initializeViewPager();
        initializeTabLayout();
    }

    private void initializeViewPager() {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        adapter.addFragment(UsageHistorySuccessfulFragment.newInstance());
        adapter.addFragment(UsageHistoryFailedFragment.newInstance());
        binding.viewPager.setAdapter(adapter);
    }

    private void initializeTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.successful)).setIcon(R.drawable.ic_task_completed));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.failed)).setIcon(R.drawable.ic_failed));
        binding.tabLayout.setSelectedTabIndicator(R.drawable.cat_tabs_rounded_line_indicator);

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
    public void onTabSelected(TabLayout.Tab tab) {
        binding.viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected String getExitMessage() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}