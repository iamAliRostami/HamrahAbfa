package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.LAST_PAGE;

import android.view.View;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityFollowRequestBinding;
import com.leon.hamrah_abfa.fragments.follow_request.FollowRequestListFragment;

public class FollowRequestActivity extends BaseActivity {
    private ActivityFollowRequestBinding binding;
    private String billId;
    private boolean lastPage;

    @Override
    protected void initialize() {
        binding = ActivityFollowRequestBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            billId = getIntent().getExtras().getString(BILL_ID.getValue());
            lastPage = getIntent().getExtras().getBoolean(LAST_PAGE.getValue());
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().add(binding.fragmentRequest.getId(),
                FollowRequestListFragment.newInstance()).commit();
    }

    @Override
    protected String getExitMessage() {
        return getString(R.string.return_by_press_again);
    }

    @Override
    public void onClick(View v) {

    }
}