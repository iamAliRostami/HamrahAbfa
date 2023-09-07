package com.app.leon.moshtarak.fragments.bottom_sheets;

import static com.leon.toast.RTLToast.success;
import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.base_items.BaseBottomSheetFragment;
import com.app.leon.moshtarak.databinding.FragmentSubmitInfoBottomBinding;
import com.app.leon.moshtarak.enums.SharedReferenceKeys;
import com.app.leon.moshtarak.fragments.cards.BillCardViewModel;
import com.app.leon.moshtarak.helpers.MyApplication;
import com.app.leon.moshtarak.requests.bill.AddBillRequest;

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
        progressStatus(!new AddBillRequest(requireContext(), viewModel,
                new AddBillRequest.ICallback() {
                    @Override
                    public void succeed(BillCardViewModel bill) {
                        insertData(bill);
                    }

                    @Override
                    public void changeUI(boolean done) {
                        progressStatus(done);
                    }
                }).request());
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
        String id = MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.ID.getValue()).concat(bill.getId()).concat(",");
        String billId = MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.BILL_ID.getValue()).concat(bill.getBillId()).concat(",");
        String alias = MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.ALIAS.getValue()).concat(bill.getAlias()).concat(",");
        String debt = MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.DEBT.getValue()).concat(bill.getDebt()).concat(",");

        String deadline = MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.DEADLINE.getValue())
                .concat(bill.getDeadline() != null ? bill.getDeadline() : "-").concat(",");

        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.ID.getValue(), id);
        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.BILL_ID.getValue(), billId);
        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.ALIAS.getValue(), alias);
        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.DEBT.getValue(), debt);
        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.DEADLINE.getValue(), deadline);

        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.IS_PAYED.getValue()
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