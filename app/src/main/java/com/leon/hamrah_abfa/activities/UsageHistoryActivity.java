package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.UUID;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.fragment_state_adapter.ViewPagerAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityUsageHistoryBinding;
import com.leon.hamrah_abfa.fragments.citizen.NotFoundFragment;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.fragments.usage_history.Attempt;
import com.leon.hamrah_abfa.fragments.usage_history.AttemptViewModel;
import com.leon.hamrah_abfa.fragments.usage_history.UsageHistoryFailedFragment;
import com.leon.hamrah_abfa.fragments.usage_history.UsageHistorySuccessfulFragment;
import com.leon.hamrah_abfa.requests.counter.GetUsageHistoryRequest;

import java.util.ArrayList;

public class UsageHistoryActivity extends BaseActivity implements TabLayout.OnTabSelectedListener,
        UsageHistorySuccessfulFragment.ICallback, UsageHistoryFailedFragment.ICallback {
    private final ArrayList<AttemptViewModel> successAttempts = new ArrayList<>();
    private final ArrayList<AttemptViewModel> unsuccessAttempts = new ArrayList<>();
    private ActivityUsageHistoryBinding binding;
    private String id;
    private DialogFragment fragment;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityUsageHistoryBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getString(UUID.getValue());
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        requestAttempts();
        initializeTabLayout();
    }

    private void requestAttempts() {
        progressStatus(new GetUsageHistoryRequest(this, new GetUsageHistoryRequest.ICallback() {
            @Override
            public void succeed(Attempt attempt) {
                successAttempts.addAll(attempt.successBills);
                unsuccessAttempts.addAll(attempt.unsuccessBills);
                initializeViewPager();
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }, id).request());
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance(this::initializeViewPager);
                showFragmentDialogOnce(this, WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    private void initializeViewPager() {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        if (successAttempts.isEmpty()) {
            adapter.addFragment(NotFoundFragment.newInstance());
        } else {
            adapter.addFragment(UsageHistorySuccessfulFragment.newInstance());
        }
        if (unsuccessAttempts.isEmpty()) {
            adapter.addFragment(NotFoundFragment.newInstance());
        } else {
            adapter.addFragment(UsageHistoryFailedFragment.newInstance());
        }
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

    @Override
    public ArrayList<AttemptViewModel> getUnsuccessBills() {
        return unsuccessAttempts;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ArrayList<AttemptViewModel> getSuccessBills() {
        return successAttempts;
    }
}