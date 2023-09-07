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
import com.app.leon.moshtarak.databinding.FragmentEditInfoBinding;
import com.app.leon.moshtarak.enums.BundleEnum;
import com.app.leon.moshtarak.enums.SharedReferenceKeys;
import com.app.leon.moshtarak.fragments.cards.BillCardViewModel;
import com.app.leon.moshtarak.fragments.dashboard.PaymentStats;
import com.app.leon.moshtarak.helpers.MyApplication;
import com.app.leon.moshtarak.requests.bill.EditBillRequest;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class EditInfoFragment extends BaseBottomSheetFragment implements View.OnClickListener {
    private final BillCardViewModel viewModel = new BillCardViewModel();
    private FragmentEditInfoBinding binding;
    private ICallback callback;

    public EditInfoFragment() {
    }

    public static EditInfoFragment newInstance(String id, String billId, String alias) {
        EditInfoFragment fragment = new EditInfoFragment();
        Bundle args = new Bundle();
        args.putString(BundleEnum.ID.getValue(), id);
        args.putString(BundleEnum.BILL_ID.getValue(), billId);
        args.putString(BundleEnum.ALIAS.getValue(), alias);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel.setAlias(getArguments().getString(BundleEnum.ALIAS.getValue()));
            viewModel.setOldAlias(getArguments().getString(BundleEnum.ALIAS.getValue()));
            viewModel.setBillId(getArguments().getString(BundleEnum.BILL_ID.getValue()));
            viewModel.setId(getArguments().getString(BundleEnum.ID.getValue()));
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
            if (checkInputs()) {
                requestEditBill();
            }
        }
    }

    private boolean checkInputs() {
        if (viewModel.getAlias() == null || viewModel.getAlias().isEmpty()) {
            warning(requireContext(), getString(R.string.enter_alias)).show();
            binding.editTextAccountTitle.setError(getString(R.string.enter_alias));
            binding.editTextAccountTitle.requestFocus();
            return false;
        }
        return true;
    }

    private void requestEditBill() {
        progressStatus(!new EditBillRequest(requireContext(), new EditBillRequest.ICallback() {
            @Override
            public void succeed(PaymentStats paymentStats) {
                editData();
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }, viewModel).request());
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
        ArrayList<String> alias = new ArrayList<>(Arrays.asList(MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.ALIAS.getValue()).split(",")));
        for (int i = 0; i < alias.size(); i++) {
            if (alias.get(i).equals(viewModel.getOldAlias())) {
                alias.set(i, viewModel.getAlias());
            }
            tempAlias.append(alias.get(i)).append(",");
        }
        MyApplication.getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.ALIAS.getValue(), tempAlias.toString());

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