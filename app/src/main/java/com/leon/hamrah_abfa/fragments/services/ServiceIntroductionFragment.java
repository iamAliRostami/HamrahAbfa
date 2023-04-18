package com.leon.hamrah_abfa.fragments.services;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.SERVICE_TYPE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.ServicesIntroductionAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.databinding.FragmentServiceIntroductionBinding;

public class ServiceIntroductionFragment extends Fragment {
    private ServicesIntroductionAdapter adapter;
    private FragmentServiceIntroductionBinding binding;
    private int serviceType;
    private String billId;

    public ServiceIntroductionFragment() {
    }

    public static ServiceIntroductionFragment newInstance(String billId, int serviceType) {
        ServiceIntroductionFragment fragment = new ServiceIntroductionFragment();
        Bundle args = new Bundle();
        args.putString(BILL_ID.getValue(), billId);
        args.putInt(SERVICE_TYPE.getValue(), serviceType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            billId = getArguments().getString(BILL_ID.getValue());
            serviceType = getArguments().getInt(SERVICE_TYPE.getValue());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceIntroductionBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        adapter = new ServicesIntroductionAdapter(requireContext(),
                R.array.services_sale_menu, R.array.services_sale_introduction, R.array.services_sale_icons);
        binding.recyclerViewMenu.setAdapter(adapter);
        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewMenu.addOnItemTouchListener(new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewMenu, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.updateSelectedService(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }
}