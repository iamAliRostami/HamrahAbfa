package com.leon.hamrah_abfa.fragments.pay_bill;

import static com.leon.toast.RTLToast.warning;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.recycler_view.PayBillAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.databinding.FragmentPayBillBaseBinding;

public class PayBillBaseFragment extends Fragment implements View.OnClickListener {
    private FragmentPayBillBaseBinding binding;
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
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_pay) {
            redirectPay();
        }
    }

    private void redirectPay() {
        StringBuilder url = new StringBuilder(getString(R.string.payment_site_multiple));
        for (int i = 0; i < adapter.getSelectedBills().size(); i++) {
            url.append("billIds=").append(adapter.getSelectedBills().get(i)).append("&");
        }
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.intent.setPackage("com.android.chrome");
            customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            customTabsIntent.launchUrl(requireContext(), Uri.parse(url.toString()));
        } catch (Exception e) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            browserIntent.setData(Uri.parse(url.toString()));
            startActivity(browserIntent);
        }
    }
}