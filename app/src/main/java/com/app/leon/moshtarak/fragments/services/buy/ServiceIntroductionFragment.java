package com.app.leon.moshtarak.fragments.services.buy;

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

import com.app.leon.moshtarak.adapters.base_adapter.ServicesIntroductionBaseAdapter;
import com.app.leon.moshtarak.adapters.recycler_view.RecyclerItemClickListener;
import com.app.leon.moshtarak.adapters.recycler_view.ServicesIntroductionMultiAdapter;
import com.app.leon.moshtarak.adapters.recycler_view.ServicesIntroductionSingleAdapter;
import com.app.leon.moshtarak.databinding.FragmentServiceIntroductionBinding;
import com.app.leon.moshtarak.fragments.services.ServicesViewModel;
import com.app.leon.moshtarak.helpers.Constants;
import com.app.leon.moshtarak.R;

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
                    callback.setAdapter(new ServicesIntroductionSingleAdapter(requireContext(), R.array.services_ab_baha_menu,
                            R.array.services_ab_baha_introduction, R.array.services_ab_baha_id, R.array.services_ab_baha_icons));
                    break;
                case 1:
                    callback.setAdapter(new ServicesIntroductionMultiAdapter(requireContext(), R.array.services_after_sale_menu,
                            R.array.services_after_sale_introduction, R.array.services_after_sale_id, R.array.services_after_sale_icons));

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
        if (callback.getServicesViewModel().getServiceType() != 1) {
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
                callback.displayView(Constants.SERVICE_PERSONAL_INFORMATION_FRAGMENT);
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

        void displayView(int position);
    }
}