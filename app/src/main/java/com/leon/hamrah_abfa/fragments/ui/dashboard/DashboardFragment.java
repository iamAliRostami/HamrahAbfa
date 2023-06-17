package com.leon.hamrah_abfa.fragments.ui.dashboard;

import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentDashboardBinding;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.utils.ShowFragment;


public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

        ShowFragment.showFragmentDialogOnce(requireContext(), WAITING.getValue(), WaitingFragment.newInstance());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}