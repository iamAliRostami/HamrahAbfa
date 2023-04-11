package com.leon.hamrah_abfa.fragments.bottom_sheets;

import static com.leon.hamrah_abfa.enums.BundleEnum.DEBT;
import static com.leon.hamrah_abfa.enums.BundleEnum.OWNER;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.BILL_ID;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.NICKNAME;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentSubmitInfoBottomBinding;
import com.leon.hamrah_abfa.fragments.ui.cards.CardViewModel;
import com.leon.hamrah_abfa.fragments.ui.home.HomeFragment;
import com.leon.toast.RTLToast;

public class SubmitInfoFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private FragmentSubmitInfoBottomBinding binding;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSubmitInfoBottomBinding.inflate(inflater, container, false);
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
                RTLToast.warning(requireContext(), getString(R.string.enter_bill_id)).show();
            } else {
                //TODO
                CardViewModel card = new CardViewModel();
                if (!viewModel.getNickname().isEmpty())
                    card.setNickname(viewModel.getNickname());
                else
                    card.setNickname(viewModel.getBillId());
                card.setBillId(viewModel.getBillId());
                card.setDebt(Integer.parseInt(viewModel.getBillId()));
                card.setOwner(viewModel.getBillId());

                insertData(card);

            }
        }
    }

    private void insertData(CardViewModel card) {
        final String billId = getApplicationComponent().SharedPreferenceModel().getStringData(BILL_ID.getValue()).concat(card.getBillId()).concat(",");
        final String nickname = getApplicationComponent().SharedPreferenceModel().getStringData(NICKNAME.getValue()).concat(card.getNickname()).concat(",");
        final String owner = getApplicationComponent().SharedPreferenceModel().getStringData(OWNER.getValue()).concat(card.getOwner()).concat(",");


        getApplicationComponent().SharedPreferenceModel().putData(BILL_ID.getValue(), billId);
        getApplicationComponent().SharedPreferenceModel().putData(NICKNAME.getValue(), nickname);
        getApplicationComponent().SharedPreferenceModel().putData(DEBT.getValue(), card.getDebt());
        getApplicationComponent().SharedPreferenceModel().putData(OWNER.getValue(), owner);

        ((HomeFragment) getParentFragment()).updateCard();

        dismiss();
    }

    interface ICallback {
        void updateCard();
    }
}