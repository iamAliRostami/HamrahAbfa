package com.leon.hamrah_abfa.fragments.bottom_sheets;

import static com.leon.hamrah_abfa.enums.BundleEnum.ALIAS;
import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.ID;
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
import com.leon.hamrah_abfa.databinding.FragmentEditInfoBinding;
import com.leon.hamrah_abfa.enums.SharedReferenceKeys;
import com.leon.hamrah_abfa.fragments.cards.BillCardViewModel;
import com.leon.hamrah_abfa.fragments.dashboard.PaymentStats;
import com.leon.hamrah_abfa.requests.bill.EditBillRequest;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class EditInfoFragment extends BaseBottomSheetFragment implements View.OnClickListener {
    private FragmentEditInfoBinding binding;
    private ICallback callback;
    private final BillCardViewModel viewModel = new BillCardViewModel();

    public EditInfoFragment() {
    }

    public static EditInfoFragment newInstance(String id, String billId, String alias) {
        EditInfoFragment fragment = new EditInfoFragment();
        Bundle args = new Bundle();
        args.putString(ID.getValue(), id);
        args.putString(BILL_ID.getValue(), billId);
        args.putString(ALIAS.getValue(), alias);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel.setAlias(getArguments().getString(ALIAS.getValue()));
            viewModel.setOldAlias(getArguments().getString(ALIAS.getValue()));
            viewModel.setBillId(getArguments().getString(BILL_ID.getValue()));
            viewModel.setId(getArguments().getString(ID.getValue()));
            getArguments().clear();
        }
    }


    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentEditInfoBinding.inflate(inflater, container, false);
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
                requestEditBill();
            }
        }
    }

    private void requestEditBill() {
        boolean isOnline = new EditBillRequest(requireContext(), new EditBillRequest.ICallback() {
            @Override
            public void succeed(PaymentStats paymentStats) {
                editData();
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }, viewModel).request();
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

    private void editData() {
        StringBuilder tempAlias = new StringBuilder();
        ArrayList<String> alias = new ArrayList<>(Arrays.asList(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.ALIAS.getValue()).split(",")));
        for (int i = 0; i < alias.size(); i++) {
            if (alias.get(i).equals(viewModel.getOldAlias())) {
                alias.set(i, viewModel.getAlias());
            }
            tempAlias.append(alias.get(i)).append(",");
        }
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.ALIAS.getValue(), tempAlias.toString());

        if (callback != null) callback.updateCard();
        success(requireContext(), getString(R.string.ensheab_has_been_edited)).show();
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