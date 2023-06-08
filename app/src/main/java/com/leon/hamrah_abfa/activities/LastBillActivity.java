package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;

import android.view.View;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityLastBillBinding;
import com.leon.hamrah_abfa.fragments.last_bill.LastBillEnsheabInfoFragment;
import com.leon.hamrah_abfa.fragments.last_bill.LastBillItemsFragment;
import com.leon.hamrah_abfa.fragments.last_bill.LastBillReadingInfoFragment;
import com.leon.hamrah_abfa.fragments.last_bill.LastBillSummaryFragment;
import com.leon.hamrah_abfa.fragments.last_bill.LastBillUsingInfoFragment;

public class LastBillActivity extends BaseActivity {
    private ActivityLastBillBinding binding;
    private String billId;

    @Override
    protected void initialize() {
        binding = ActivityLastBillBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            billId = getIntent().getExtras().getString(BILL_ID.getValue());
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        initializeFrameLayouts();
        setOnClickListener();
    }

    private void setOnClickListener() {
        binding.linearLayoutItems.setOnClickListener(this);
        binding.linearLayoutEnsheab.setOnClickListener(this);
        binding.linearLayoutSummary.setOnClickListener(this);
        binding.linearLayoutUsingInfo.setOnClickListener(this);
        binding.linearLayoutReadingInfo.setOnClickListener(this);
    }

    private void initializeFrameLayouts() {
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

        if (id == R.id.linear_layout_summary) {
            if (visibility == View.GONE) {
                binding.frameLayoutSummary.setVisibility(View.VISIBLE);
                binding.imageViewArrowSummary.setImageResource(R.drawable.arrow_up);
            } else {
                binding.imageViewArrowSummary.setImageResource(R.drawable.arrow_down);
            }
        } else if (id == R.id.linear_layout_items) {
            if (visibility == View.GONE) {
                binding.frameLayoutItems.setVisibility(View.VISIBLE);
                binding.imageViewArrowItems.setImageResource(R.drawable.arrow_up);
            } else {
                binding.imageViewArrowItems.setImageResource(R.drawable.arrow_down);
            }
        } else if (id == R.id.linear_layout_using_info) {
            if (visibility == View.GONE) {
                binding.frameLayoutUsingInfo.setVisibility(View.VISIBLE);
                binding.imageViewArrowUsingInfo.setImageResource(R.drawable.arrow_up);
            } else {
                binding.imageViewArrowUsingInfo.setImageResource(R.drawable.arrow_down);
            }
        }else if (id == R.id.linear_layout_reading_info) {
            if (visibility == View.GONE) {
                binding.frameLayoutReadingInfo.setVisibility(View.VISIBLE);
                binding.imageViewArrowReadingInfo.setImageResource(R.drawable.arrow_up);
            } else {
                binding.imageViewArrowReadingInfo.setImageResource(R.drawable.arrow_down);
            }
        }else if (id == R.id.linear_layout_ensheab) {
            if (visibility == View.GONE) {
                binding.frameLayoutEnsheabInfo.setVisibility(View.VISIBLE);
                binding.imageViewArrowEnsheabInfo.setImageResource(R.drawable.arrow_up);
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
}