package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.UUID;
import static com.leon.hamrah_abfa.enums.FragmentTags.FOLLOW_REQUEST_TRACK;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.warning;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.fragment_state_adapter.ViewPagerAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RequestHistoryAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityFollowRequestBinding;
import com.leon.hamrah_abfa.fragments.citizen.NotFoundFragment;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.fragments.follow_request.FollowRequestListFinishedFragment;
import com.leon.hamrah_abfa.fragments.follow_request.FollowRequestListUnfinishedFragment;
import com.leon.hamrah_abfa.fragments.follow_request.FollowRequestTrackFragment;
import com.leon.hamrah_abfa.fragments.follow_request.MasterHistory;
import com.leon.hamrah_abfa.fragments.follow_request.RequestInfo;
import com.leon.hamrah_abfa.requests.follow_request.GetMasterHistoryRequest;

import java.util.ArrayList;

public class FollowRequestActivity extends BaseActivity implements TabLayout.OnTabSelectedListener,
        FollowRequestListFinishedFragment.ICallback, FollowRequestListUnfinishedFragment.ICallback,
        FollowRequestTrackFragment.ICallback {
    private MasterHistory requestInfo = new MasterHistory();
    private RequestHistoryAdapter adapterUnfinished;
    private RequestHistoryAdapter adapterFinished;
    private ActivityFollowRequestBinding binding;
    private DialogFragment fragment;
    private String filterValue = "";
    private String uuid;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityFollowRequestBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            uuid = getIntent().getExtras().getString(UUID.getValue());
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        binding.floatButtonSearch.setOnClickListener(this);
        requestMasterHistory();
        initializeTabLayout();
    }

    private void requestMasterHistory() {
        progressStatus(new GetMasterHistoryRequest(this, new GetMasterHistoryRequest.ICallback() {
            @Override
            public void succeed(MasterHistory requestInfoAll) {
                requestInfo = requestInfoAll;
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }, uuid).request());
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance();
                showFragmentDialogOnce(this, WAITING.getValue(), fragment);
            }
        } else {
            initializeViewPager();
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    private void initializeViewPager() {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        if (requestInfo.unfinisheds.isEmpty()) {
            adapter.addFragment(NotFoundFragment.newInstance());
        } else {
            adapterUnfinished = new RequestHistoryAdapter(this, requestInfo.unfinisheds);
            adapter.addFragment(FollowRequestListUnfinishedFragment.newInstance());
        }

        if (requestInfo.finisheds.isEmpty()) {
            adapter.addFragment(NotFoundFragment.newInstance());
        } else {
            adapterFinished = new RequestHistoryAdapter(this, requestInfo.finisheds);
            adapter.addFragment(FollowRequestListFinishedFragment.newInstance());
        }
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
    public RequestHistoryAdapter getFinishedAdapter() {
        return adapterFinished;
    }

    @Override
    public RequestHistoryAdapter getUnfinishedAdapter() {
        return adapterUnfinished;
    }

    @Override
    public int getUnfinishedTrackNumber(int position) {
        return adapterUnfinished.getTrackNumber(position);
    }

    @Override
    public int getFinishedTrackNumber(int position) {
        return adapterFinished.getTrackNumber(position);
    }

    @Override
    public void filter() {
        ArrayList<RequestInfo> requestFinishedTemp = new ArrayList<>();
        for (RequestInfo info : requestInfo.finisheds) {
            String trackNumber = String.valueOf(info.trackNumber);
            if (trackNumber.toLowerCase().contains(filterValue.toLowerCase()))
                requestFinishedTemp.add(info);
        }
        ArrayList<RequestInfo> requestUnfinishedTemp = new ArrayList<>();
        for (RequestInfo info : requestInfo.unfinisheds) {
            String trackNumber = String.valueOf(info.trackNumber);
            if (trackNumber.toLowerCase().contains(filterValue.toLowerCase()))
                requestUnfinishedTemp.add(info);
        }
        if (!requestUnfinishedTemp.isEmpty()) {
            adapterUnfinished.filterList(requestUnfinishedTemp);
        }
        if (!requestFinishedTemp.isEmpty()) {
            adapterFinished.filterList(requestFinishedTemp);
        }
        if (requestUnfinishedTemp.isEmpty() && requestFinishedTemp.isEmpty()) {
            warning(this, R.string.not_found).show();
        }
    }

    @Override
    public String getFilterValue() {
        return filterValue;
    }

    @Override
    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }
}