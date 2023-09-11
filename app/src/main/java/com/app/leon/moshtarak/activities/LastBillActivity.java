package com.app.leon.moshtarak.activities;

import static com.app.leon.moshtarak.enums.BundleEnum.ID;
import static com.app.leon.moshtarak.enums.BundleEnum.UUID;
import static com.app.leon.moshtarak.enums.BundleEnum.ZONE_ID;
import static com.app.leon.moshtarak.enums.FragmentTags.WAITING;
import static com.app.leon.moshtarak.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.base_items.BaseActivity;
import com.app.leon.moshtarak.databinding.ActivityLastBillBinding;
import com.app.leon.moshtarak.fragments.citizen.NotFoundFragment;
import com.app.leon.moshtarak.fragments.dialog.WaitingFragment;
import com.app.leon.moshtarak.fragments.last_bill.BillViewModel;
import com.app.leon.moshtarak.fragments.last_bill.LastBillEnsheabInfoFragment;
import com.app.leon.moshtarak.fragments.last_bill.LastBillItemsFragment;
import com.app.leon.moshtarak.fragments.last_bill.LastBillReadingInfoFragment;
import com.app.leon.moshtarak.fragments.last_bill.LastBillSummaryFragment;
import com.app.leon.moshtarak.fragments.last_bill.LastBillUsingInfoFragment;
import com.app.leon.moshtarak.requests.bill.GetBillRequest;

public class LastBillActivity extends BaseActivity implements LastBillSummaryFragment.ICallback,
        LastBillItemsFragment.ICallback, LastBillReadingInfoFragment.ICallback,
        LastBillUsingInfoFragment.ICallback, LastBillEnsheabInfoFragment.ICallback {
    private ActivityLastBillBinding binding;
    private DialogFragment fragment;
    private BillViewModel bill;
    private String uuid;
    private int zoneId;
    private int id;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityLastBillBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            uuid = getIntent().getExtras().getString(UUID.getValue());
            zoneId = getIntent().getExtras().getInt(ZONE_ID.getValue());
            id = getIntent().getExtras().getInt(ID.getValue());
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        requestBills();
        setOnClickListener();
    }

    private void requestBills() {
        GetBillRequest.ICallback callback = new GetBillRequest.ICallback() {
            @Override
            public void succeed(BillViewModel bill) {
                LastBillActivity.this.bill = bill;
//                initializeFrameLayouts();
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        };
        progressStatus(getBillRequest(callback).request());
    }

    private GetBillRequest getBillRequest(GetBillRequest.ICallback callback) {
        if (zoneId == 0)
            return new GetBillRequest(this, callback, uuid);
        else return new GetBillRequest(this, callback, id, zoneId);
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance(this::finish);
                showFragmentDialogOnce(this, WAITING.getValue(), fragment);
            }
        } else {
            initializeFrameLayouts();
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    private void setOnClickListener() {
        binding.linearLayoutItems.setOnClickListener(this);
        binding.linearLayoutEnsheab.setOnClickListener(this);
        binding.linearLayoutSummary.setOnClickListener(this);
        binding.linearLayoutUsingInfo.setOnClickListener(this);
        binding.linearLayoutReadingInfo.setOnClickListener(this);
    }

    private void initializeFrameLayouts() {
        if (bill != null) {
            getSupportFragmentManager().beginTransaction().replace(binding.frameLayoutSummary.getId(),
                    LastBillSummaryFragment.newInstance()).commitNow();
            getSupportFragmentManager().beginTransaction().replace(binding.frameLayoutUsingInfo.getId(),
                    LastBillUsingInfoFragment.newInstance()).commitNow();
            getSupportFragmentManager().beginTransaction().replace(binding.frameLayoutItems.getId(),
                    LastBillItemsFragment.newInstance()).commitNow();
            getSupportFragmentManager().beginTransaction().replace(binding.frameLayoutReadingInfo.getId(),
                    LastBillReadingInfoFragment.newInstance()).commitNow();
            getSupportFragmentManager().beginTransaction().replace(binding.frameLayoutEnsheabInfo.getId(),
                    LastBillEnsheabInfoFragment.newInstance()).commitNow();
        } else {
            getSupportFragmentManager().beginTransaction().replace(binding.frameLayoutSummary.getId(),
                    NotFoundFragment.newInstance()).commitNow();
            getSupportFragmentManager().beginTransaction().replace(binding.frameLayoutUsingInfo.getId(),
                    NotFoundFragment.newInstance()).commitNow();
            getSupportFragmentManager().beginTransaction().replace(binding.frameLayoutItems.getId(),
                    NotFoundFragment.newInstance()).commitNow();
            getSupportFragmentManager().beginTransaction().replace(binding.frameLayoutReadingInfo.getId(),
                    NotFoundFragment.newInstance()).commitNow();
            getSupportFragmentManager().beginTransaction().replace(binding.frameLayoutEnsheabInfo.getId(),
                    NotFoundFragment.newInstance()).commitNow();
        }
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        final int visibility = getVisibility(id);
        makeViewsGone();
        resetImageViews();
        resetBackground();
        if (id == R.id.linear_layout_summary) {
            if (visibility == View.GONE) {
                binding.frameLayoutSummary.setVisibility(View.VISIBLE);
                binding.imageViewArrowSummary.setImageResource(R.drawable.arrow_up);
                binding.relativeLayoutSummary.setBackgroundResource(R.drawable.background_last_bill);
            } else {
                binding.imageViewArrowSummary.setImageResource(R.drawable.arrow_down);
            }
        } else if (id == R.id.linear_layout_items) {
            if (visibility == View.GONE) {
                binding.frameLayoutItems.setVisibility(View.VISIBLE);
                binding.imageViewArrowItems.setImageResource(R.drawable.arrow_up);
                binding.relativeLayoutItems.setBackgroundResource(R.drawable.background_last_bill);
            } else {
                binding.imageViewArrowItems.setImageResource(R.drawable.arrow_down);
            }
        } else if (id == R.id.linear_layout_using_info) {
            if (visibility == View.GONE) {
                binding.frameLayoutUsingInfo.setVisibility(View.VISIBLE);
                binding.imageViewArrowUsingInfo.setImageResource(R.drawable.arrow_up);
                binding.relativeLayoutUsingInfo.setBackgroundResource(R.drawable.background_service_introduction);
            } else {
                binding.imageViewArrowUsingInfo.setImageResource(R.drawable.arrow_down);
            }
        } else if (id == R.id.linear_layout_reading_info) {
            if (visibility == View.GONE) {
                binding.frameLayoutReadingInfo.setVisibility(View.VISIBLE);
                binding.imageViewArrowReadingInfo.setImageResource(R.drawable.arrow_up);
                binding.relativeLayoutReadingInfo.setBackgroundResource(R.drawable.background_service_introduction);
            } else {
                binding.imageViewArrowReadingInfo.setImageResource(R.drawable.arrow_down);
            }
        } else if (id == R.id.linear_layout_ensheab) {
            if (visibility == View.GONE) {
                binding.frameLayoutEnsheabInfo.setVisibility(View.VISIBLE);
                binding.imageViewArrowEnsheabInfo.setImageResource(R.drawable.arrow_up);
                binding.relativeLayoutEnsheabInfo.setBackgroundResource(R.drawable.background_last_bill);
            } else {
                binding.imageViewArrowEnsheabInfo.setImageResource(R.drawable.arrow_down);
            }
        }
    }

    private void resetImageViews() {
        binding.imageViewArrowItems.setImageResource(R.drawable.arrow_down);
        binding.imageViewArrowSummary.setImageResource(R.drawable.arrow_down);
        binding.imageViewArrowUsingInfo.setImageResource(R.drawable.arrow_down);
        binding.imageViewArrowReadingInfo.setImageResource(R.drawable.arrow_down);
        binding.imageViewArrowEnsheabInfo.setImageResource(R.drawable.arrow_down);
    }

    private int getVisibility(int id) {
        if (id == R.id.linear_layout_using_info)
            return binding.frameLayoutUsingInfo.getVisibility();
        else if (id == R.id.linear_layout_items)
            return binding.frameLayoutItems.getVisibility();
        else if (id == R.id.linear_layout_reading_info)
            return binding.frameLayoutReadingInfo.getVisibility();
        else if (id == R.id.linear_layout_ensheab)
            return binding.frameLayoutEnsheabInfo.getVisibility();
        else
            return binding.frameLayoutSummary.getVisibility();
    }

    private void makeViewsGone() {
        binding.frameLayoutItems.setVisibility(View.GONE);
        binding.frameLayoutSummary.setVisibility(View.GONE);
        binding.frameLayoutUsingInfo.setVisibility(View.GONE);
        binding.frameLayoutEnsheabInfo.setVisibility(View.GONE);
        binding.frameLayoutReadingInfo.setVisibility(View.GONE);
    }

    private void resetBackground() {
        binding.relativeLayoutReadingInfo.setBackgroundResource(android.R.color.transparent);
        binding.relativeLayoutUsingInfo.setBackgroundResource(android.R.color.transparent);
        binding.relativeLayoutEnsheabInfo.setBackgroundResource(R.color.light_gray);
        binding.relativeLayoutSummary.setBackgroundResource(R.color.light_gray);
        binding.relativeLayoutItems.setBackgroundResource(R.color.light_gray);
    }

    @Override
    public BillViewModel getBillViewModel() {
        return bill;
    }
}