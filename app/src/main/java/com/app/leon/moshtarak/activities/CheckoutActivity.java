package com.app.leon.moshtarak.activities;

import static com.app.leon.moshtarak.enums.BundleEnum.UUID;
import static com.app.leon.moshtarak.enums.FragmentTags.WAITING;
import static com.app.leon.moshtarak.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.app.leon.moshtarak.adapters.fragment_state_adapter.ViewPagerAdapter;
import com.app.leon.moshtarak.base_items.BaseActivity;
import com.app.leon.moshtarak.databinding.ActivityCheckoutBinding;
import com.app.leon.moshtarak.fragments.checkout.CheckoutBillFragment;
import com.app.leon.moshtarak.fragments.checkout.CheckoutBillViewModel;
import com.app.leon.moshtarak.fragments.checkout.CheckoutPaymentFragment;
import com.app.leon.moshtarak.fragments.checkout.CheckoutPaymentViewModel;
import com.app.leon.moshtarak.fragments.checkout.Kardex;
import com.app.leon.moshtarak.fragments.citizen.NotFoundFragment;
import com.app.leon.moshtarak.fragments.dialog.WaitingFragment;
import com.app.leon.moshtarak.requests.bill.GetKardexRequest;
import com.google.android.material.tabs.TabLayout;
import com.app.leon.moshtarak.R;

import java.util.ArrayList;

public class CheckoutActivity extends BaseActivity implements TabLayout.OnTabSelectedListener,
        CheckoutBillFragment.ICallback, CheckoutPaymentFragment.ICallback {
    private final ArrayList<CheckoutPaymentViewModel> payments = new ArrayList<>();
    private final ArrayList<CheckoutBillViewModel> bills = new ArrayList<>();
    private ActivityCheckoutBinding binding;
    private DialogFragment fragment;
    private String id;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getString(UUID.getValue());
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        requestBills();

        initializeTabLayout();
    }

    private void requestBills() {
        progressStatus(new GetKardexRequest(this, new GetKardexRequest.ICallback() {
            @Override
            public void succeed(Kardex kardex) {
                bills.addAll(kardex.bills);
                payments.addAll(kardex.payments);
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
        if (bills.isEmpty()) {
            adapter.addFragment(NotFoundFragment.newInstance());
        } else
            adapter.addFragment(CheckoutBillFragment.newInstance());
        if (payments.isEmpty()) {
            adapter.addFragment(NotFoundFragment.newInstance());
        } else {
            adapter.addFragment(CheckoutPaymentFragment.newInstance());
        }
        binding.viewPager.setAdapter(adapter);
    }

    private void initializeTabLayout() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.bills)).setIcon(R.drawable.ic_bill));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.payments)).setIcon(R.drawable.ic_paid_bill));
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
    public String getId() {
        return id;
    }

    @Override
    public ArrayList<CheckoutBillViewModel> getBills() {
        return bills;
    }

    @Override
    public ArrayList<CheckoutPaymentViewModel> getPayments() {
        return payments;
    }
}