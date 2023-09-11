package com.app.leon.moshtarak.activities;

import static com.app.leon.moshtarak.enums.BundleEnum.UUID;
import static com.app.leon.moshtarak.enums.FragmentTags.WAITING;
import static com.app.leon.moshtarak.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.adapters.fragment_state_adapter.ViewPagerAdapter;
import com.app.leon.moshtarak.base_items.BaseActivity;
import com.app.leon.moshtarak.databinding.ActivityUsageHistoryBinding;
import com.app.leon.moshtarak.fragments.citizen.NotFoundFragment;
import com.app.leon.moshtarak.fragments.dialog.WaitingFragment;
import com.app.leon.moshtarak.fragments.usage_history.Attempt;
import com.app.leon.moshtarak.fragments.usage_history.AttemptViewModel;
import com.app.leon.moshtarak.fragments.usage_history.UsageHistoryFailedFragment;
import com.app.leon.moshtarak.fragments.usage_history.UsageHistorySuccessfulFragment;
import com.app.leon.moshtarak.requests.counter.GetUsageHistoryRequest;
import com.google.android.material.tabs.TabLayout;

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
            initializeViewPager();
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