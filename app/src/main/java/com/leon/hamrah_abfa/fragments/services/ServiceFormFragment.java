package com.leon.hamrah_abfa.fragments.services;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentServiceFormBinding;

public class ServiceFormFragment extends Fragment implements View.OnClickListener {
    private FragmentServiceFormBinding binding;
    private ICallback serviceActivity;

    public ServiceFormFragment() {
    }

    public static ServiceFormFragment newInstance() {
        return new ServiceFormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentServiceFormBinding.inflate(inflater, container, false);
        binding.setViewModel(serviceActivity.getServicesViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.getRoot().post(() -> Log.e("size", String.valueOf(binding.mapView.getMeasuredWidth())));
        Log.e("size 1", String.valueOf(binding.linearLayoutButtons.getMeasuredWidth()));
        int size = binding.linearLayoutButtons.getMeasuredWidth();
        Log.e("size 2", String.valueOf(size));
        binding.mapView.getLayoutParams().height = binding.buttonPrevious.getMeasuredWidth();
//        binding.mapView.getLayoutParams().height = 500;

        Log.e("size", String.valueOf(binding.mapView.getMeasuredWidth()));
        Log.e("size", String.valueOf(binding.cardViewContent.getMeasuredWidth()));
        size = binding.buttonPrevious.getMeasuredWidth();
        Log.e("size 2", String.valueOf(size));
        Log.e("size", String.valueOf(binding.buttonPrevious.getMeasuredWidth()));
//        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//        );
//        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT,
////                binding.mapView.getLayoutParams().width,
//                1.0f
//        );
//
//        binding.mapView.measure(1, 1);
//        int size = binding.mapView.getWidth();
//
////        new RelativeLayout.LayoutParams(
////                ViewGroup.LayoutParams.MATCH_PARENT,
//////                ViewGroup.LayoutParams.MATCH_PARENT,
////                binding.mapView.getLayoutParams().width,
////                1.0f
////        );
//        Log.e("size", String.valueOf(size));
//        params1.width = size;
//        params1.height = size;
//        binding.mapView.setLayoutParams(params1);

//        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
////                ViewGroup.LayoutParams.MATCH_PARENT,
//                binding.mapView.getLayoutParams().width,
//                1.0f
//        );
//
//        binding.mapView.setLayoutParams(param);

        binding.imageViewIcon.setImageDrawable(serviceActivity.getServicesViewModel().getDrawable());
        binding.buttonSubmit.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            serviceActivity.submitUserInfo();
        } else if (id == R.id.button_previous) {
            serviceActivity.goServices();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) serviceActivity = (ICallback) context;
    }

    public interface ICallback {
        void submitUserInfo();

        void goServices();

        ServicesViewModel getServicesViewModel();
    }
}
