package com.leon.hamrah_abfa.fragments.services;

import static com.leon.hamrah_abfa.enums.BundleEnum.SERVICE_TYPE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.recycler_view.ServicesIntroductionMultiAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.ServicesIntroductionSingleAdapter;
import com.leon.hamrah_abfa.databinding.FragmentServiceIntroductionBinding;
import com.leon.hamrah_abfa.infrastructure.ServicesIntroductionBaseAdapter;
import com.leon.toast.RTLToast;

import java.util.ArrayList;

public class ServiceIntroductionFragment extends Fragment implements View.OnClickListener {
    private ServicesIntroductionBaseAdapter adapter;
    private FragmentServiceIntroductionBinding binding;
    private ICallback serviceActivity;
    private int serviceType;

    public ServiceIntroductionFragment() {
    }

    public static ServiceIntroductionFragment newInstance(int serviceType) {
        final ServiceIntroductionFragment fragment = new ServiceIntroductionFragment();
        final Bundle args = new Bundle();
        args.putInt(SERVICE_TYPE.getValue(), serviceType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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
        switch (serviceType) {
            case 2:
                adapter = new ServicesIntroductionMultiAdapter(requireContext(), R.array.services_after_sale_menu,
                        R.array.services_after_sale_introduction, R.array.services_after_sale_id, R.array.services_after_sale_icons);
                //TODO
                break;
            case 1:
                adapter = new ServicesIntroductionSingleAdapter(requireContext(), R.array.services_ab_baha_menu,
                        R.array.services_ab_baha_introduction, R.array.services_ab_baha_id, R.array.services_ab_baha_icons);
                break;
            case 0:
            default:
                adapter = new ServicesIntroductionSingleAdapter(requireContext(), R.array.services_sale_menu,
                        R.array.services_sale_introduction, R.array.services_sale_id, R.array.services_sale_icons);
                break;
        }
        binding.recyclerViewMenu.setAdapter(adapter);
        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            if (!adapter.selectedServiceId().isEmpty()) {
                serviceActivity.submitServices(adapter.selectedServiceId(), adapter.selectedServiceTitle());
            } else {
                RTLToast.warning(requireContext(), R.string.choose_a_service).show();
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) serviceActivity = (ICallback) context;
    }

    public interface ICallback {
        void submitServices(ArrayList<Integer> selectedServicesId, ArrayList<String> selectedServicesTitle);
    }
}