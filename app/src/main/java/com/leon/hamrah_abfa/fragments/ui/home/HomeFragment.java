package com.leon.hamrah_abfa.fragments.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.MenuAdapter;
import com.leon.hamrah_abfa.databinding.FragmentHomeBinding;
import com.leon.toast.RTLToast;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        final MenuAdapter adapter = new MenuAdapter(requireContext(), new ArrayList<>(Arrays.asList("Buenos Aires", "Córdoba", "La Plata", "lksdlk"))
                , new ArrayList<>(Arrays.asList(R.drawable.help, R.drawable.write, R.drawable.abfa_logo_simple, R.drawable.ic_dashboard_black_24dp)));

//        final MenuAdapter adapter = new MenuAdapter(requireContext(), new ArrayList<>(R.array.menu_home)
//                , new ArrayList<>(R.array.icons_home));

//        final MenuAdapter adapter = new MenuAdapter(requireContext(), new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menu_home)))
//                , getResources().obtainTypedArray(R.array.icons_home));
        binding.gridViewMenu.setAdapter(adapter);
//        binding.gridViewMenu.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        RTLToast.info(requireContext(), String.valueOf(position)).show();
        Log.e("item", String.valueOf(position));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}