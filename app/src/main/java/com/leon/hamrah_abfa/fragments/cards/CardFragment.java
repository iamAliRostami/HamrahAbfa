package com.leon.hamrah_abfa.fragments.cards;

import static com.leon.hamrah_abfa.enums.BundleEnum.ALIAS;
import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.DEBT;
import static com.leon.hamrah_abfa.enums.BundleEnum.ID;
import static com.leon.hamrah_abfa.enums.FragmentTags.ASK_YES_NO;
import static com.leon.hamrah_abfa.enums.FragmentTags.EDIT_INFO;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.enums.SharedReferenceKeys.DEADLINE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.success;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentCardBinding;
import com.leon.hamrah_abfa.enums.SharedReferenceKeys;
import com.leon.hamrah_abfa.fragments.bottom_sheets.EditInfoFragment;
import com.leon.hamrah_abfa.fragments.dashboard.PaymentStats;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.fragments.dialog.YesNoFragment;
import com.leon.hamrah_abfa.requests.bill.RemoveBillRequest;
import com.leon.toast.RTLToast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;


public class CardFragment extends Fragment implements View.OnClickListener {
    private FragmentCardBinding binding;
    private BillCardViewModel viewModel;
    private DialogFragment fragment;
    private ICallback callback;

    public CardFragment() {
    }

    public static CardFragment newInstance(BillCardViewModel bill) {
        final CardFragment fragment = new CardFragment();
        final Bundle args = new Bundle();
        args.putString(ID.getValue(), bill.getId());
        args.putString(BILL_ID.getValue(), bill.getBillId());
        args.putString(ALIAS.getValue(), bill.getAlias());
        args.putString(DEBT.getValue(), bill.getDebt());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            viewModel = new BillCardViewModel(getArguments().getString(ID.getValue()),
                    getArguments().getString(BILL_ID.getValue()), getArguments().getString(ALIAS.getValue()),
                    getArguments().getString(DEBT.getValue()));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCardBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.textViewPay.setOnClickListener(this);
        binding.imageViewEdit.setOnClickListener(this);
        binding.imageViewDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.text_view_pay) {
            if (viewModel.isPayed()) {
                RTLToast.warning(requireContext(), getString(R.string.no_debt)).show();
            } else {
                redirectPay();
            }
        } else if (id == R.id.image_view_edit) {
            showFragmentDialogOnce(requireContext(), EDIT_INFO.getValue(), EditInfoFragment.newInstance(viewModel.getId(),
                    viewModel.getBillId(), viewModel.getAlias()));
        } else if (id == R.id.image_view_delete) {
            showRemoveDialog();
        }
    }

    private void redirectPay() {
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage("com.android.chrome");
            customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            customTabsIntent.launchUrl(requireContext(), Uri.parse(String.format(getString(R.string.payment_site), viewModel.getBillId())));
        } catch (Exception e) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            browserIntent.setData(Uri.parse(String.format(getString(R.string.payment_site), viewModel.getBillId())));
            startActivity(browserIntent);
        }
    }

    private void showRemoveDialog() {
        showFragmentDialogOnce(requireContext(), ASK_YES_NO.getValue(),
                YesNoFragment.newInstance(R.drawable.ic_delete_1, getString(R.string.delete_account),
                        getString(R.string.are_u_sure_deleting), getString(R.string.delete_account)
                        , getString(R.string.cancel), new YesNoFragment.IClickListener() {
                            @Override
                            public void yes(DialogFragment fragment) {
                                fragment.dismiss();
                                //TODO
                                requestRemoveBill();
                            }

                            @Override
                            public void no(DialogFragment fragment) {
                                fragment.dismiss();
                            }
                        })
        );
    }

    private void requestRemoveBill() {
        progressStatus(new RemoveBillRequest(requireContext(), new RemoveBillRequest.ICallback() {
            @Override
            public void succeed(PaymentStats paymentStats) {
                removeData();
            }

            @Override
            public void changeUI(boolean show) {
                progressStatus(show);
            }
        }, viewModel.getId()).request());
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

    private void removeData() {
        ArrayList<String> ids = new ArrayList<>(Arrays.asList(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.ID.getValue()).split(",")));
        ArrayList<String> billIds = new ArrayList<>(Arrays.asList(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.BILL_ID.getValue()).split(",")));
        ArrayList<String> aliases = new ArrayList<>(Arrays.asList(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.ALIAS.getValue()).split(",")));
        ArrayList<String> debts = new ArrayList<>(Arrays.asList(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(SharedReferenceKeys.DEBT.getValue()).split(",")));
        ArrayList<String> deadlines = new ArrayList<>(Arrays.asList(getInstance().getApplicationComponent().SharedPreferenceModel().getStringData(DEADLINE.getValue()).split(",")));

        StringBuilder id = new StringBuilder();
        StringBuilder billId = new StringBuilder();
        StringBuilder alias = new StringBuilder();
        StringBuilder debt = new StringBuilder();
        StringBuilder deadline = new StringBuilder();


        for (int i = 0; i < ids.size(); i++) {
            if (!billIds.get(i).equals(viewModel.getBillId())) {
                id.append(ids.get(i)).append(",");
                billId.append(billIds.get(i)).append(",");
                alias.append(aliases.get(i)).append(",");
                debt.append(debts.get(i)).append(",");
                deadline.append(deadlines.get(i)).append(",");
            }
        }

        getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.ID.getValue(), id.toString());
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.BILL_ID.getValue(), billId.toString());
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.ALIAS.getValue(), alias.toString());
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(SharedReferenceKeys.DEBT.getValue(), debt.toString());
        getInstance().getApplicationComponent().SharedPreferenceModel().putData(DEADLINE.getValue(), deadline.toString());

        if (callback != null) callback.updateCard();
        success(requireContext(), getString(R.string.ensheab_has_been_deleted)).show();
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