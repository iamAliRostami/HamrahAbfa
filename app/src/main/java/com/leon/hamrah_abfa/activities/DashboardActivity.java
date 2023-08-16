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
import com.leon.hamrah_abfa.fragments.dashboard.CounterStats;
import com.leon.hamrah_abfa.fragments.dashboard.DashboardCounterFragment;
import com.leon.hamrah_abfa.fragments.dashboard.DashboardPaymentFragment;
import com.leon.hamrah_abfa.fragments.dashboard.DashboardSummaryFragment;
import com.leon.hamrah_abfa.fragments.dashboard.PaymentStats;
import com.leon.hamrah_abfa.fragments.dashboard.SummaryStats;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.requests.dashboard.GetBillSummaryRequest;
import com.leon.hamrah_abfa.requests.dashboard.GetCounterStatRequest;
import com.leon.hamrah_abfa.requests.dashboard.GetPaymentStatRequest;

public class DashboardActivity extends BaseActivity implements DashboardSummaryFragment.ICallback,
        DashboardCounterFragment.ICallback, DashboardPaymentFragment.ICallback {
    private ActivityDashboardBinding binding;
    private DialogFragment fragment;
    private String billId;
    private String uuid;
    private SummaryStats summaryStats = new SummaryStats();
    private CounterStats counterStats = new CounterStats();
    private PaymentStats paymentStats = new PaymentStats();


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
        showPaymentStat(true);
        requestSummaryBill();

    }

    private void setOnClickListener() {
        binding.linearLayoutSummary.setOnClickListener(this);
        binding.linearLayoutCounter.setOnClickListener(this);
        binding.linearLayoutPayment.setOnClickListener(this);
    }

    private void requestSummaryBill() {
        boolean isOnline = new GetBillSummaryRequest(this, new GetBillSummaryRequest.ICallback() {

            @Override
            public void succeed(SummaryStats billSummary) {
                DashboardActivity.this.summaryStats = billSummary;
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
        requestPaymentStat();
    }

    private void requestPaymentStat() {
        boolean isOnline = new GetPaymentStatRequest(this, new GetPaymentStatRequest.ICallback() {
            @Override
            public void succeed(PaymentStats paymentStats) {
                DashboardActivity.this.paymentStats = paymentStats;
                showPaymentStat(false);
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

    private void showPaymentStat(boolean v) {
        switchPayment(v);
        getSupportFragmentManager().beginTransaction().add(binding.fragmentPayment.getId(),
                DashboardPaymentFragment.newInstance()).commitNow();
    }

    @Override
    public SummaryStats getBillSummary() {
        return summaryStats;
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
        } else if (id == R.id.linear_layout_payment) {
            switchPayment(binding.fragmentPayment.getVisibility() == View.VISIBLE);
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

    private void switchPayment(boolean v) {
        if (v) {
            binding.fragmentPayment.setVisibility(View.GONE);
            binding.imageViewArrowPayment.setImageResource(R.drawable.arrow_down);
        } else {
            binding.fragmentPayment.setVisibility(View.VISIBLE);
            binding.imageViewArrowPayment.setImageResource(R.drawable.arrow_up);
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

    @Override
    public PaymentStats getPaymentStats() {
        return paymentStats;
    }
}