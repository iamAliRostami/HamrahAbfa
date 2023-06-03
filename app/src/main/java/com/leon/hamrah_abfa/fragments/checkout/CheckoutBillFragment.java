package com.leon.hamrah_abfa.fragments.checkout;

import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leon.hamrah_abfa.adapters.recycler_view.CheckoutBillAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.NewsAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.databinding.FragmentCheckoutBillBinding;

public class CheckoutBillFragment extends Fragment {
    private FragmentCheckoutBillBinding binding;
    private CheckoutBillAdapter adapter;
    private ICallback callback;

    public CheckoutBillFragment() {
    }

    public static CheckoutBillFragment newInstance() {
        return new CheckoutBillFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckoutBillBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        adapter = new CheckoutBillAdapter(requireContext());
        binding.recyclerViewCheckoutBill.setAdapter(adapter);
        binding.recyclerViewCheckoutBill.setLayoutManager(new LinearLayoutManager(requireContext()));
        setRecyclerViewListener();
    }

    private void setRecyclerViewListener() {
        final RecyclerItemClickListener listener = new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewCheckoutBill, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        binding.recyclerViewCheckoutBill.addOnItemTouchListener(listener);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface ICallback {
        String getBillId();
    }
}