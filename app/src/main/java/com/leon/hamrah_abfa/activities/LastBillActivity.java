package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;

import android.view.View;

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

    }

    private void initializeFrameLayouts() {
        getSupportFragmentManager().beginTransaction().replace(binding.frameLayoutSummary.getId(),
                LastBillSummaryFragment.newInstance()).commitNow();
        getSupportFragmentManager().beginTransaction().replace(binding.frameLayoutUsing.getId(),
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
    }
}