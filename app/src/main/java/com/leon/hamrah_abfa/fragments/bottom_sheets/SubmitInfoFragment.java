package com.leon.hamrah_abfa.fragments.bottom_sheets;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEBT;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.ALIAS;
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
import com.leon.hamrah_abfa.fragments.ui.cards.BillCardViewModel;
import com.leon.hamrah_abfa.utils.bill.AddBillRequest;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

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
            if (viewModel.getBillId().isEmpty()) {
                warning(requireContext(), getString(R.string.enter_bill_id)).show();
            } else {
                final ArrayList<String> billIds = new ArrayList<>(Arrays.asList(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue()).split(",")));
                for (int i = 0; i < billIds.size(); i++) {
                    if (billIds.get(i).equals(viewModel.getBillId())) {
                        warning(requireContext(), getString(R.string.bill_id_repetitive)).show();
                        return;
                    }
                }
                //TODO
                if (viewModel.getAlias() == null || viewModel.getAlias().isEmpty())
                    viewModel.setAlias(viewModel.getBillId());
                viewModel.setDebt(Integer.parseInt(viewModel.getBillId()));
                requestAddBill();
                insertData();

            }
        } else if (id == R.id.image_view_arrow_down) {
            dismiss();
        }
    }

    private void requestAddBill() {
        new AddBillRequest(requireContext(), viewModel, new AddBillRequest.ICallback() {
            @Override
            public void succeed() {
                //TODO
                insertData();
            }

            @Override
            public void changeUI(boolean done) {

            }
        }).request();
    }

    private void insertData() {
        //TODO
        final String billId = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue()).concat(viewModel.getBillId()).concat(",");
        final String nickname = getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(ALIAS.getValue()).concat(viewModel.getAlias()).concat(",");

        getInstance().getApplicationComponent().SharedPreferenceModel().putData(BILL_ID.getValue(), billId);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(ALIAS.getValue(), nickname);
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), viewModel.getDebt());
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