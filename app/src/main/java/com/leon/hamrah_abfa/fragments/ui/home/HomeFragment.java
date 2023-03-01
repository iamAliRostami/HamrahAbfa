package com.leon.hamrah_abfa.fragments.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.MenuAdapter;
import com.leon.hamrah_abfa.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        final MenuAdapter adapter = new MenuAdapter(requireContext(), new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menu_home)))
                , getResources().obtainTypedArray(R.array.icons_home));
        binding.gridViewMenu.setAdapter(adapter);
        binding.gridViewMenu.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}