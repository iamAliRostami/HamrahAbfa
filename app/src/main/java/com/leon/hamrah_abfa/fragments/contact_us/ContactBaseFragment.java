package com.leon.hamrah_abfa.fragments.contact_us;

import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_SUGGESTION_FRAGMENT;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.MenuAdapter;
import com.leon.hamrah_abfa.databinding.FragmentContactBaseBinding;

public class ContactBaseFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private FragmentContactBaseBinding binding;
    private ICallback callback;

    public ContactBaseFragment() {
    }

    public static ContactBaseFragment newInstance() {
        return new ContactBaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactBaseBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeGridView();
        binding.imageViewBack.setOnClickListener(this);
    }

    private void initializeGridView() {
        final MenuAdapter adapter = new MenuAdapter(requireContext(), R.array.contact_abfa_menu, R.array.contact_abfa_icons);
        binding.gridViewMenu.setAdapter(adapter);
        binding.gridViewMenu.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.image_view_back) {
            requireActivity().finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            callback.displayView(CONTACT_SUGGESTION_FRAGMENT);
        } else if (position == 1) {
        } else if (position == 2) {
        } else if (position == 3) {
        } else if (position == 4) {
        } else if (position == 5) {
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        void displayView(int position);
    }
}