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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentSubmitInfoBottomBinding;
import com.leon.hamrah_abfa.fragments.ui.cards.CardViewModel;
import com.leon.toast.RTLToast;

public class SubmitInfoFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private FragmentSubmitInfoBottomBinding binding;

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
            if (binding.editTextBillId.getText().toString().isEmpty()) {

                RTLToast.warning(requireContext(), getString(R.string.enter_bill_id)).show();
            } else {
                //TODO
                CardViewModel card = new CardViewModel();
                if (!binding.editTextAccountTitle.getText().toString().isEmpty())
                    card.setNickname(binding.editTextAccountTitle.getText().toString());
                else
                    card.setNickname(binding.editTextBillId.getText().toString());
                card.setBillId(binding.editTextBillId.getText().toString());
                card.setDebt(Integer.parseInt(binding.editTextBillId.getText().toString()));
                card.setOwner(binding.editTextBillId.getText().toString());

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

//        FragmentManager fm = getFragmentManager();
//        MainFragment fragm = (MainFragment)fm.findFragmentById(R.id.main_fragment);
//        fragm.otherList();

        dismiss();
    }

    interface ICallback{
        void updateCard();
    }
}