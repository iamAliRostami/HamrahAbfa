package com.app.leon.moshtarak.fragments.citizen;

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

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.adapters.recycler_view.CitizenAdapter;
import com.app.leon.moshtarak.adapters.recycler_view.RecyclerItemClickListener;
import com.app.leon.moshtarak.databinding.FragmentCitizenListBinding;
import com.app.leon.moshtarak.helpers.Constants;

public class CitizenListFragment extends Fragment implements View.OnClickListener {
    private FragmentCitizenListBinding binding;
    private ICallback callback;
//    private CitizenAdapter adapter;

    public CitizenListFragment() {
    }

    public static CitizenListFragment newInstance() {
        return new CitizenListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCitizenListBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeRecyclerView();
        binding.buttonSubmit.setOnClickListener(this);
    }

    private void initializeRecyclerView() {
        binding.recyclerViewMenu.setAdapter(callback.getCitizenAdapter());
        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewListener();
    }

    private void recyclerViewListener() {
        final RecyclerItemClickListener listener = new RecyclerItemClickListener(requireContext(),
                binding.recyclerViewMenu, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                callback.getCitizenAdapter().updateSelectedService(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        binding.recyclerViewMenu.addOnItemTouchListener(listener);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            if (callback.getCitizenAdapter().selectedTitle() == null ||
                    callback.getCitizenAdapter().selectedTitle().isEmpty()) {
                warning(requireContext(), R.string.choose_a_option).show();
            } else {
                callback.setServices(callback.getCitizenAdapter().selectedTitle());
                callback.displayView(Constants.CITIZEN_BASE_FRAGMENT);
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {

        void setServices(String selectedTitle);

        void displayView(int position);

        CitizenAdapter getCitizenAdapter();
    }
}