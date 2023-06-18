package com.leon.hamrah_abfa.fragments.services;

import static com.leon.hamrah_abfa.helpers.Constants.SERVICE_FORM_FRAGMENT;
import static com.leon.toast.RTLToast.warning;

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
import com.leon.hamrah_abfa.adapters.base_adapter.ServicesIntroductionBaseAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.RecyclerItemClickListener;
import com.leon.hamrah_abfa.adapters.recycler_view.ServicesIntroductionMultiAdapter;
import com.leon.hamrah_abfa.adapters.recycler_view.ServicesIntroductionSingleAdapter;
import com.leon.hamrah_abfa.databinding.FragmentServiceIntroductionBinding;

import java.util.ArrayList;

public class ServiceIntroductionFragment extends Fragment implements View.OnClickListener {
    private FragmentServiceIntroductionBinding binding;
    private ICallback callback;

    public ServiceIntroductionFragment() {
    }

    public static ServiceIntroductionFragment newInstance() {
        return new ServiceIntroductionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        binding.recyclerViewMenu.setAdapter(getAdapter());
        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewListener();
    }

    private ServicesIntroductionBaseAdapter getAdapter() {
        if (callback.getAdapter() == null) {
            //TODO
            switch (callback.getServicesViewModel().getServiceType()) {
                case 2:
                    callback.setAdapter(new ServicesIntroductionMultiAdapter(requireContext(), R.array.services_after_sale_menu,
                            R.array.services_after_sale_introduction, R.array.services_after_sale_id, R.array.services_after_sale_icons));
                    break;
                case 1:
                    callback.setAdapter(new ServicesIntroductionSingleAdapter(requireContext(), R.array.services_ab_baha_menu,
                            R.array.services_ab_baha_introduction, R.array.services_ab_baha_id, R.array.services_ab_baha_icons));
                    break;
                case 0:
                default:
                    callback.setAdapter(new ServicesIntroductionSingleAdapter(requireContext(), R.array.services_sale_menu,
                            R.array.services_sale_introduction, R.array.services_sale_id, R.array.services_sale_icons));
                    break;
            }
        }
        return callback.getAdapter();
    }

    private void recyclerViewListener() {
        if (callback.getServicesViewModel().getServiceType() != 2) {
            final RecyclerItemClickListener listener = new RecyclerItemClickListener(requireContext(),
                    binding.recyclerViewMenu, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    callback.getAdapter().updateSelectedService(position);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            binding.recyclerViewMenu.addOnItemTouchListener(listener);
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            if (!callback.getAdapter().selectedServiceId().isEmpty()) {
                callback.setServices(callback.getAdapter().selectedServiceId(), callback.getAdapter().selectedServiceTitle());
                callback.displayView(SERVICE_FORM_FRAGMENT,true);
            } else {
                warning(requireContext(), R.string.choose_a_service).show();
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {

        ServicesIntroductionBaseAdapter getAdapter();

        void setAdapter(ServicesIntroductionBaseAdapter adapter);

        ServicesViewModel getServicesViewModel();

        void setServices(ArrayList<Integer> selectedServicesId, ArrayList<String> selectedServicesTitle);
        void displayView(int position, boolean next);
    }
}