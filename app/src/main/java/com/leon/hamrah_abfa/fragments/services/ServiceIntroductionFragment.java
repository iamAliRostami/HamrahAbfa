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

import com.leon.hamrah_abfa.Computer;
import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.ServicesIntroductionMultiAdapter;
import com.leon.hamrah_abfa.adapters.ServicesIntroductionSingleAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.databinding.FragmentServiceIntroductionBinding;
import com.leon.hamrah_abfa.infrastructure.ServicesIntroductionBaseAdapter;

public class ServiceIntroductionFragment extends Fragment implements View.OnClickListener {
    private ServicesIntroductionBaseAdapter adapter;
    private FragmentServiceIntroductionBinding binding;
    private int serviceType;
    private String billId;

    public ServiceIntroductionFragment() {
    }

    public static ServiceIntroductionFragment newInstance(String billId, int serviceType) {
        final ServiceIntroductionFragment fragment = new ServiceIntroductionFragment();
        final Bundle args = new Bundle();
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
        binding.buttonSubmit.setOnClickListener(this);
    }

    private void initializeRecyclerView() {
        Computer comp = new Computer.ComputerBuilder(
                "500 GB", "2 GB").setBluetoothEnabled(true)
                .setGraphicsCardEnabled(true).build();

        switch (serviceType) {
            case 1:
                adapter = new ServicesIntroductionMultiAdapter(requireContext(), R.array.services_sale_menu,
                        R.array.services_sale_introduction, R.array.services_sale_id, R.array.services_sale_icons);
                break;
            case 2:
                break;
            case 0:
            default:
                adapter = new ServicesIntroductionSingleAdapter(requireContext(), R.array.services_sale_menu,
                        R.array.services_sale_introduction, R.array.services_sale_id, R.array.services_sale_icons);
                break;
        }

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

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {

        }
    }
}