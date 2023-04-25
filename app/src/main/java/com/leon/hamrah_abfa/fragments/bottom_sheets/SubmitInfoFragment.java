package com.leon.hamrah_abfa.fragments.bottom_sheets;

import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEBT;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.NICKNAME;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.OWNER;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;
import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentSubmitInfoBottomBinding;
import com.leon.hamrah_abfa.fragments.ui.cards.CardViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class SubmitInfoFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private FragmentSubmitInfoBottomBinding binding;
    private ICallback callback;
    private final CardViewModel viewModel = new CardViewModel();

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSubmitInfoBottomBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            if (viewModel.getBillId().isEmpty()) {
                warning(requireContext(), getString(R.string.enter_bill_id)).show();
            } else {
                final ArrayList<String> billIds = new ArrayList<>(Arrays.asList(getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue()).split(",")));
                for (int i = 0; i < billIds.size(); i++) {
                    if (billIds.get(i).equals(viewModel.getBillId())) {
                        warning(requireContext(), getString(R.string.bill_id_repetitive)).show();
                        return;
                    }
                }
                //TODO
                if (viewModel.getNickname() == null || viewModel.getNickname().isEmpty())
                    viewModel.setNickname(viewModel.getBillId());
                viewModel.setDebt(Integer.parseInt(viewModel.getBillId()));
                viewModel.setOwner(viewModel.getBillId());
                insertData();

            }
        }
    }

    private void insertData() {
        final String billId = getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue()).concat(viewModel.getBillId()).concat(",");
        final String nickname = getApplicationComponent().SharedPreferenceModel().getStringData(NICKNAME.getValue()).concat(viewModel.getNickname()).concat(",");
        final String owner = getApplicationComponent().SharedPreferenceModel().getStringData(OWNER.getValue()).concat(viewModel.getOwner()).concat(",");

        getApplicationComponent().SharedPreferenceModel().putData(BILL_ID.getValue(), billId);
        getApplicationComponent().SharedPreferenceModel().putData(NICKNAME.getValue(), nickname);
        getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), viewModel.getDebt());
        getApplicationComponent().SharedPreferenceModel().putData(OWNER.getValue(), owner);
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