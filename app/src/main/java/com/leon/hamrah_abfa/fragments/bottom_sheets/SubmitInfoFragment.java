package com.leon.hamrah_abfa.fragments.bottom_sheets;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ALIAS;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEBT;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ID;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
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
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonSubmit.setOnClickListener(this);
        binding.imageViewArrowDown.setOnClickListener(this);
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
//                final ArrayList<String> billIds = new ArrayList<>(Arrays.asList(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue()).split(",")));
//                for (int i = 0; i < billIds.size(); i++) {
//                    if (billIds.get(i).equals(viewModel.getBillId())) {
//                        warning(requireContext(), getString(R.string.bill_id_repetitive)).show();
//                        return;
//                    }
//                }
                //TODO
                if (viewModel.getAlias() == null || viewModel.getAlias().isEmpty())
                    viewModel.setAlias(viewModel.getBillId());
                requestAddBill();

            }
        } else if (id == R.id.image_view_arrow_down) {
            dismiss();
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
        bill.setDebtString(String.valueOf(bill.getDebt()));
        String id = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ID.getValue()).concat(bill.getId()).concat(",");
        String billId = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue()).concat(bill.getBillId()).concat(",");
        String alias = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ALIAS.getValue()).concat(bill.getAlias()).concat(",");
        String debt = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(DEBT.getValue()).concat(bill.getDebtString()).concat(",");


        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ID.getValue(), id);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(BILL_ID.getValue(), billId);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ALIAS.getValue(), alias);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), debt);

        if (callback != null) callback.updateCard();
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