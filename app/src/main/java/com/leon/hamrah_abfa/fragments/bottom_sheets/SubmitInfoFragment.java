package com.leon.hamrah_abfa.fragments.bottom_sheets;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ALIAS;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEADLINE;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEBT;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.IS_PAYED;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.toast.RTLToast.success;
import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.base_items.BaseBottomSheetFragment;
import com.leon.hamrah_abfa.databinding.FragmentSubmitInfoBottomBinding;
import com.leon.hamrah_abfa.fragments.cards.BillCardViewModel;
import com.leon.hamrah_abfa.requests.bill.AddBillRequest;

import org.jetbrains.annotations.NotNull;

public class SubmitInfoFragment extends BaseBottomSheetFragment {
    private final BillCardViewModel viewModel = new BillCardViewModel();
    private FragmentSubmitInfoBottomBinding binding;
    private ICallback callback;

    public SubmitInfoFragment() {
    }

    public static SubmitInfoFragment newInstance() {
        return new SubmitInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentSubmitInfoBottomBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.buttonSubmit.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        final int id = v.getId();
        if (id == R.id.button_submit) {
            if (viewModel.getBillId() == null || viewModel.getBillId().isEmpty()) {
                warning(requireContext(), getString(R.string.enter_bill_id)).show();
                binding.editTextBillId.setError(getString(R.string.enter_bill_id));
                binding.editTextBillId.requestFocus();
            } else {
                requestAddBill();
            }
        }
    }

    private void requestAddBill() {
        boolean isOnline = new AddBillRequest(requireContext(), viewModel, new AddBillRequest.ICallback() {
            @Override
            public void succeed(BillCardViewModel bill) {
                //TODO
                insertData(bill);
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }).request();
        progressStatus(!isOnline);
    }

    private void progressStatus(boolean hide) {
        setCancelable(hide);
        if (hide) {
            binding.buttonSubmit.setVisibility(View.VISIBLE);
            binding.lottieAnimationView.setVisibility(View.GONE);
            binding.lottieAnimationView.pauseAnimation();
        } else {
            binding.buttonSubmit.setVisibility(View.GONE);
            binding.lottieAnimationView.playAnimation();
            binding.lottieAnimationView.setVisibility(View.VISIBLE);
        }
    }

    private void insertData(BillCardViewModel bill) {
        //TODO
//        bill.setDebt(String.valueOf(bill.getDebt()));
        String id = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ID.getValue()).concat(bill.getId()).concat(",");
        String billId = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue()).concat(bill.getBillId()).concat(",");
        String alias = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ALIAS.getValue()).concat(bill.getAlias()).concat(",");
        String debt = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(DEBT.getValue()).concat(bill.getDebt()).concat(",");

        String deadline = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(DEADLINE.getValue())
                .concat(bill.getDeadline() != null ? bill.getDeadline() : "-").concat(",");


        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ID.getValue(), id);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(BILL_ID.getValue(), billId);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ALIAS.getValue(), alias);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), debt);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEADLINE.getValue(), deadline);

        getInstance().getApplicationComponent().SharedPreferenceModel().putData(IS_PAYED.getValue()
                .concat(bill.getBillId()), bill.isPayed());

        if (callback != null) callback.updateCard();
        success(requireContext(), getString(R.string.ensheab_has_been_added)).show();
        dismiss();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        void updateCard();
    }
}