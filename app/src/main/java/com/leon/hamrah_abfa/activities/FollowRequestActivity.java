package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.UUID;
import static com.leon.hamrah_abfa.enums.FragmentTags.FOLLOW_REQUEST_TRACK;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.fragment_state_adapter.ViewPagerAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RequestAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityFollowRequestBinding;
import com.leon.hamrah_abfa.fragments.follow_request.FollowRequestListFinishedFragment;
import com.leon.hamrah_abfa.fragments.follow_request.FollowRequestListUnfinishedFragment;
import com.leon.hamrah_abfa.fragments.follow_request.FollowRequestTrackFragment;
import com.leon.hamrah_abfa.fragments.follow_request.RequestInfo;
import com.leon.hamrah_abfa.fragments.follow_request.RequestInfoAll;

import java.util.ArrayList;

public class FollowRequestActivity extends BaseActivity implements TabLayout.OnTabSelectedListener,
        FollowRequestListFinishedFragment.ICallback, FollowRequestListUnfinishedFragment.ICallback {
    private ActivityFollowRequestBinding binding;
    private RequestInfoAll requestInfo = new RequestInfoAll();
    private RequestAdapter adapterFinished;
    private RequestAdapter adapterUnfinished;

    private String uuid;

    @Override
    protected void initialize() {
        binding = ActivityFollowRequestBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            uuid = getIntent().getExtras().getString(UUID.getValue());
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());

        binding.floatButtonSearch.setOnClickListener(this);
        initializeViewPager();
        initializeTabLayout();
    }

    private void initializeViewPager() {
        adapterFinished = new RequestAdapter(this, requestInfo.finisheds);
        adapterUnfinished = new RequestAdapter(this, requestInfo.unfinisheds);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        adapter.addFragment(FollowRequestListUnfinishedFragment.newInstance());
        adapter.addFragment(FollowRequestListFinishedFragment.newInstance());
        binding.viewPager.setAdapter(adapter);
    }

    private void initializeTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.unfinished_requests)).setIcon(R.drawable.unfinished_request));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.finished_requests)).setIcon(R.drawable.finished_request));
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
    protected String getExitMessage() {
        return getString(R.string.return_by_press_again);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.float_button_search) {
            showFragmentDialogOnce(v.getContext(), FOLLOW_REQUEST_TRACK.getValue(), FollowRequestTrackFragment.newInstance());
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

    @Override
    public ArrayList<RequestInfo> getRequestInfoUnfinished() {
        return requestInfo.finisheds;
    }

    @Override
    public ArrayList<RequestInfo> getRequestInfoFinished() {
        return requestInfo.unfinisheds;
    }

    @Override
    public RequestAdapter getFinishedAdapter() {
        return adapterFinished;
    }

    @Override
    public RequestAdapter getUnfinishedAdapter() {
        return adapterUnfinished;
    }
}