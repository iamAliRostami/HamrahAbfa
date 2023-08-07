package com.leon.hamrah_abfa.fragments.ui.dashboard;

import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentDashboardBinding;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.requests.GetBillSummaryRequest;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    private ArrayList<DashboardViewModel> billSummary = new ArrayList<>();
    private DialogFragment fragment;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        requestSummaryBill();
    }

    private void requestSummaryBill() {
        boolean isOnline = new GetBillSummaryRequest(requireContext(), new GetBillSummaryRequest.ICallback() {

            @Override
            public void succeed(ArrayList<DashboardViewModel> billSummary) {
                DashboardFragment.this.billSummary.addAll(billSummary);
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }).request();
        progressStatus(isOnline);
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance();
                showFragmentDialogOnce(requireContext(), WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}