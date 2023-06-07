package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;

import android.view.View;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityLastBillBinding;
import com.leon.hamrah_abfa.fragments.last_bill.LastBillSummaryFragment;

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
        getSupportFragmentManager().beginTransaction()
                .replace(binding.frameLayoutSummary.getId(), LastBillSummaryFragment.newInstance())
                .commitNow();
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