package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.LAST_PAGE;
import static com.leon.hamrah_abfa.enums.FragmentTags.FOLLOW_REQUEST_TRACK;
import static com.leon.hamrah_abfa.utils.ShowFragmentDialog.ShowFragmentDialogOnce;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.fragment_state_adapter.ViewPagerAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityFollowRequestBinding;
import com.leon.hamrah_abfa.fragments.follow_request.FollowRequestListFragment;
import com.leon.hamrah_abfa.fragments.follow_request.FollowRequestTrackFragment;

public class FollowRequestActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {
    private ActivityFollowRequestBinding binding;
    private String billId;
    private boolean lastPage;

    @Override
    protected void initialize() {
        binding = ActivityFollowRequestBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            billId = getIntent().getExtras().getString(BILL_ID.getValue());
            lastPage = getIntent().getExtras().getBoolean(LAST_PAGE.getValue());
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());

        binding.floatButtonSearch.setOnClickListener(this);
        initializeViewPager();
        initializeTabLayout();
//        getSupportFragmentManager().beginTransaction().add(binding.fragmentRequest.getId(),
//                FollowRequestListFragment.newInstance()).commit();
    }

    private void initializeViewPager() {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        adapter.addFragment(FollowRequestListFragment.newInstance(false));
        adapter.addFragment(FollowRequestListFragment.newInstance(true));
        binding.viewPager.setAdapter(adapter);
    }

    private void initializeTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.unfinished_requests)).setIcon(R.drawable.unfinished_request));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.finished_requests)).setIcon(R.drawable.finished_request));
        binding.tabLayout.setSelectedTabIndicator(R.drawable.cat_tabs_rounded_line_indicator);

//        setUnseenNotificationNumber();
//        setUnseenNewsNumber();
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
        return getString(R.string.return_by_press_again);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.float_button_search) {
            ShowFragmentDialogOnce(v.getContext(), FOLLOW_REQUEST_TRACK.getValue(), FollowRequestTrackFragment.newInstance());
        }
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