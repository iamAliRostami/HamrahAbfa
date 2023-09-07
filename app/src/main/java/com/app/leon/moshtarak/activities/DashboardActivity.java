package com.app.leon.moshtarak.activities;

import static com.app.leon.moshtarak.enums.BundleEnum.UUID;
import static com.app.leon.moshtarak.enums.FragmentTags.WAITING;
import static com.app.leon.moshtarak.helpers.Constants.FONT_NAME;
import static com.app.leon.moshtarak.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.base_items.BaseActivity;
import com.app.leon.moshtarak.databinding.ActivityDashboardBinding;
import com.app.leon.moshtarak.fragments.dashboard.CounterStats;
import com.app.leon.moshtarak.fragments.dashboard.DashboardCounterFragment;
import com.app.leon.moshtarak.fragments.dashboard.DashboardPaymentFragment;
import com.app.leon.moshtarak.fragments.dashboard.DashboardSummaryFragment;
import com.app.leon.moshtarak.fragments.dashboard.PaymentStats;
import com.app.leon.moshtarak.fragments.dashboard.SummaryStats;
import com.app.leon.moshtarak.fragments.dialog.WaitingFragment;
import com.app.leon.moshtarak.requests.dashboard.GetBillSummaryRequest;
import com.app.leon.moshtarak.requests.dashboard.GetCounterStatRequest;
import com.app.leon.moshtarak.requests.dashboard.GetPaymentStatRequest;

import java.util.Collections;

public class DashboardActivity extends BaseActivity implements DashboardSummaryFragment.ICallback,
        DashboardCounterFragment.ICallback, DashboardPaymentFragment.ICallback {
    private ActivityDashboardBinding binding;
    private DialogFragment fragment;
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
//            billId = getIntent().getExtras().getString(BILL_ID.getValue());
            uuid = getIntent().getExtras().getString(UUID.getValue());
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        setOnClickListener();
        showBillSummary(false);
        showCounterStat(false);
        showPaymentStat(false);
        requestSummaryBill();

    }

    private void setOnClickListener() {
        binding.linearLayoutSummary.setOnClickListener(this);
        binding.linearLayoutCounter.setOnClickListener(this);
        binding.linearLayoutPayment.setOnClickListener(this);
    }

    private void requestSummaryBill() {
        progressStatus(new GetBillSummaryRequest(this, new GetBillSummaryRequest.ICallback() {

            @Override
            public void succeed(SummaryStats billSummary) {
                DashboardActivity.this.summaryStats = billSummary;
                showBillSummary(true);
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done, () -> showBillSummary(false));
            }
        }, uuid).request());
        requestCounterStat();
    }

    private void requestCounterStat() {
        progressStatus(new GetCounterStatRequest(this, new GetCounterStatRequest.ICallback() {

            @Override
            public void succeed(CounterStats counterStats) {
                DashboardActivity.this.counterStats = counterStats;
                showCounterStat(true);
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done, () -> showCounterStat(false));
            }
        }, uuid).request());
        requestPaymentStat();
    }

    private void requestPaymentStat() {
        progressStatus(new GetPaymentStatRequest(this, new GetPaymentStatRequest.ICallback() {
            @Override
            public void succeed(PaymentStats paymentStats) {
                if (paymentStats.payDeadlineValues != null && paymentStats.payDeadlineKeys != null) {
                    Collections.reverse(DashboardActivity.this.paymentStats.payDeadlineValues);
                    Collections.reverse(DashboardActivity.this.paymentStats.payDeadlineKeys);
                    DashboardActivity.this.paymentStats = paymentStats;
                }
                showPaymentStat(true);
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done, () -> showPaymentStat(false));
            }
        }, uuid).request());
    }

    private void progressStatus(boolean show, WaitingFragment.ICancelListener... cancelListener) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance(cancelListener[0]);
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
            switchSummary(binding.fragmentSummary.getVisibility() != View.VISIBLE);
        } else if (id == R.id.linear_layout_counter) {
            switchCounter(binding.fragmentCounter.getVisibility() != View.VISIBLE);
        } else if (id == R.id.linear_layout_payment) {
            switchPayment(binding.fragmentPayment.getVisibility() != View.VISIBLE);
        }
    }

    private void switchSummary(boolean v) {
        if (v) {
            binding.fragmentSummary.setVisibility(View.VISIBLE);
            binding.imageViewArrowSummary.setImageResource(R.drawable.arrow_up);
        } else {
            binding.fragmentSummary.setVisibility(View.GONE);
            binding.imageViewArrowSummary.setImageResource(R.drawable.arrow_down);
        }
    }

    private void switchCounter(boolean v) {
        if (v) {
            binding.fragmentCounter.setVisibility(View.VISIBLE);
            binding.imageViewArrowCounter.setImageResource(R.drawable.arrow_up);
        } else {
            binding.fragmentCounter.setVisibility(View.GONE);
            binding.imageViewArrowCounter.setImageResource(R.drawable.arrow_down);
        }
    }

    private void switchPayment(boolean v) {
        if (v) {
            binding.fragmentPayment.setVisibility(View.VISIBLE);
            binding.imageViewArrowPayment.setImageResource(R.drawable.arrow_up);
        } else {
            binding.fragmentPayment.setVisibility(View.GONE);
            binding.imageViewArrowPayment.setImageResource(R.drawable.arrow_down);
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