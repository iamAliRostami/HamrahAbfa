package com.leon.hamrah_abfa.fragments.pay_bill;

import static com.leon.hamrah_abfa.enums.FragmentTags.REQUEST_DONE;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.warning;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.recycler_view.PayBillAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.databinding.FragmentPayBillBaseBinding;
import com.leon.hamrah_abfa.fragments.dialog.PayBillSucceedFragment;

public class PayBillBaseFragment extends Fragment implements View.OnClickListener {
    private FragmentPayBillBaseBinding binding;
    private ICallback callback;
    private PayBillAdapter adapter;

    public PayBillBaseFragment() {
    }

    public static PayBillBaseFragment newInstance() {
        return new PayBillBaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPayBillBaseBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeRecyclerView();
        setButtonText();
        setTextPrice();
        binding.buttonPay.setOnClickListener(this);
    }

    private void initializeRecyclerView() {
        adapter = new PayBillAdapter(requireContext());
        binding.recyclerViewPayBill.setAdapter(adapter);
        binding.recyclerViewPayBill.setLayoutManager(new LinearLayoutManager(requireContext()));
        setRecyclerViewListener();
    }

    private void setRecyclerViewListener() {
        final RecyclerItemClickListener listener = new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewPayBill, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (adapter.isPayed(position)) {
                    warning(requireContext(), R.string.no_debt).show();
                } else {
                    adapter.updateSelectedBill(position);
                    setButtonText();
                    setTextPrice();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        binding.recyclerViewPayBill.addOnItemTouchListener(listener);
    }

    @SuppressLint("DefaultLocale")
    private void setButtonText() {
        binding.buttonPay.setText(String.format("پرداخت (%d) قبض", adapter.getSelectedNumber()));
        binding.buttonPay.setEnabled(adapter.getSelectedNumber() > 0);
        binding.linearLayout.setBackgroundResource(adapter.getSelectedNumber() > 0 ?
                R.drawable.background_blue : R.drawable.background_white_blue);
    }

    @SuppressLint("DefaultLocale")
    private void setTextPrice() {
        binding.textViewPrice.setText(String.format("(%,d) ریال", adapter.getSelectedPrice()));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
            callback = (ICallback) context;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_pay) {

            warning(requireContext(), R.string.will_be_ok_soon).show();
//            warning(requireContext(), "پرداخت").show();
//            showFragmentDialogOnce(requireContext(), REQUEST_DONE.getValue(), PayBillSucceedFragment.newInstance("123456",
//                    DialogFragment::dismiss));
        }
    }

    public interface ICallback {
        void displayView(int position);
    }
}