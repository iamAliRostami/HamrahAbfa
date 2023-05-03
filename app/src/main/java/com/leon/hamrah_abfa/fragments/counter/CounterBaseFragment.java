package com.leon.hamrah_abfa.fragments.counter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.databinding.FragmentCounterBaseBinding;

public class CounterBaseFragment extends Fragment {
    private ICallback counterActivity;
    private FragmentCounterBaseBinding binding;

    public CounterBaseFragment() {
    }

    public static CounterBaseFragment newInstance() {
        return new CounterBaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCounterBaseBinding.inflate(inflater, container, false);
        binding.setViewModel(counterActivity.getViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) counterActivity = (ICallback) context;
    }

    public interface ICallback {
        CounterViewModel getViewModel();
    }
}