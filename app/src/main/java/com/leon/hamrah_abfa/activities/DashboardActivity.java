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

import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityDashboardBinding;
import com.leon.hamrah_abfa.fragments.dashboard.DashboardSummaryFragment;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.fragments.ui.dashboard.DashboardSummaryViewModel;
import com.leon.hamrah_abfa.requests.GetBillSummaryRequest;

import java.util.ArrayList;

public class DashboardActivity extends BaseActivity implements DashboardSummaryFragment.ICallback {
    private ActivityDashboardBinding binding;
    private DialogFragment fragment;
    private String billId;
    private String uuid;
    private ArrayList<DashboardSummaryViewModel> billSummary = new ArrayList<>();


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
        requestSummaryBill();

    }

    private void requestSummaryBill() {
        boolean isOnline = new GetBillSummaryRequest(this, new GetBillSummaryRequest.ICallback() {

            @Override
            public void succeed(ArrayList<DashboardSummaryViewModel> billSummary) {
                showBillSummary(billSummary);
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

    private void showBillSummary(ArrayList<DashboardSummaryViewModel> billSummary) {
        this.billSummary.addAll(billSummary);
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentSummary.getId(),
                DashboardSummaryFragment.newInstance()).commitNow();

    }

    @Override
    public ArrayList<DashboardSummaryViewModel> getBillSummary() {
        return billSummary;
    }

    @Override
    protected String getExitMessage() {
        return null;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public Typeface getTypeface() {
        return Typeface.createFromAsset(getAssets(), FONT_NAME);
    }
}