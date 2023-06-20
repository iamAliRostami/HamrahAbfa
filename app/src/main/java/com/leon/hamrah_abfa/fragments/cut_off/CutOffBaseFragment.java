package com.leon.hamrah_abfa.fragments.cut_off;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentCutOffBaseBinding;

public class CutOffBaseFragment extends Fragment {
    private FragmentCutOffBaseBinding binding;
    private ICallback callback;

    public CutOffBaseFragment() {
    }

    public static CutOffBaseFragment newInstance() {
        return new CutOffBaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCutOffBaseBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;


    }

    public interface ICallback {
        CutOffViewModel getViewModel();
    }
}