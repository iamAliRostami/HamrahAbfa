package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.UUID;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.helpers.Constants.FONT_NAME;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityDashboardBinding;
import com.leon.hamrah_abfa.fragments.dashboard.DashboardCounterFragment;
import com.leon.hamrah_abfa.fragments.dashboard.DashboardSummaryFragment;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.fragments.ui.dashboard.BillSummary;
import com.leon.hamrah_abfa.fragments.ui.dashboard.CounterStats;
import com.leon.hamrah_abfa.requests.GetCounterStatRequest;
import com.leon.hamrah_abfa.requests.dashboard.GetBillSummaryRequest;

public class DashboardActivity extends BaseActivity implements DashboardSummaryFragment.ICallback,
        DashboardCounterFragment.ICallback {
    private ActivityDashboardBinding binding;
    private DialogFragment fragment;
    private String billId;
    private String uuid;
    private BillSummary billSummary = new BillSummary();
    private CounterStats counterStats = new CounterStats();


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            billId = getIntent().getExtras().getString(BILL_ID.getValue());
            uuid = getIntent().getExtras().getString(UUID.getValue());
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        setOnClickListener();
        showBillSummary(true);
        showCounterStat(true);
        requestSummaryBill();

    }

    private void setOnClickListener() {
        binding.linearLayoutSummary.setOnClickListener(this);
        binding.linearLayoutCounter.setOnClickListener(this);
    }

    private void requestSummaryBill() {
        boolean isOnline = new GetBillSummaryRequest(this, new GetBillSummaryRequest.ICallback() {

            @Override
            public void succeed(BillSummary billSummary) {
                DashboardActivity.this.billSummary = billSummary;
                showBillSummary(false);
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }, uuid).request();
        progressStatus(isOnline);
        requestCounterStat();
    }

    private void requestCounterStat() {
        boolean isOnline = new GetCounterStatRequest(this, new GetCounterStatRequest.ICallback() {

            @Override
            public void succeed(CounterStats counterStats) {
                DashboardActivity.this.counterStats = counterStats;
                showCounterStat(false);
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }, uuid).request();
        progressStatus(isOnline);
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance();
                showFragmentDialogOnce(this, WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    private void showBillSummary(boolean v) {
        switchSummary(v);
        getSupportFragmentManager().beginTransaction().add(binding.fragmentSummary.getId(),
                DashboardSummaryFragment.newInstance()).commitNow();

    }

    private void showCounterStat(boolean v) {
        switchCounter(v);
        getSupportFragmentManager().beginTransaction().add(binding.fragmentCounter.getId(),
                DashboardCounterFragment.newInstance()).commitNow();

    }

    @Override
    public BillSummary getBillSummary() {
        return billSummary;
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.linear_layout_summary) {
            switchSummary(binding.fragmentSummary.getVisibility() == View.VISIBLE);
        } else if (id == R.id.linear_layout_counter) {
            switchCounter(binding.fragmentCounter.getVisibility() == View.VISIBLE);
        }
    }

    private void switchSummary(boolean v) {
        if (v) {
            binding.fragmentSummary.setVisibility(View.GONE);
            binding.imageViewArrowSummary.setImageResource(R.drawable.arrow_down);
        } else {
            binding.fragmentSummary.setVisibility(View.VISIBLE);
            binding.imageViewArrowSummary.setImageResource(R.drawable.arrow_up);
        }
    }

    private void switchCounter(boolean v) {
        if (v) {
            binding.fragmentCounter.setVisibility(View.GONE);
            binding.imageViewArrowCounter.setImageResource(R.drawable.arrow_down);
        } else {
            binding.fragmentCounter.setVisibility(View.VISIBLE);
            binding.imageViewArrowCounter.setImageResource(R.drawable.arrow_up);
        }
    }

    @Override
    public Typeface getTypeface() {
        return Typeface.createFromAsset(getAssets(), FONT_NAME);
    }

    @Override
    public CounterStats getCounterStat() {
        return counterStats;
    }
}