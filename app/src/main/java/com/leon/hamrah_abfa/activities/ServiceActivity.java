package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.SERVICE_TYPE;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.ActivityServiceBinding;
import com.leon.hamrah_abfa.fragments.services.ServiceFormFragment;
import com.leon.hamrah_abfa.fragments.services.ServiceIntroductionFragment;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity implements ServiceIntroductionFragment.ICallback {
    private int serviceType;
    private String billId;
    private ActivityServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            billId = getIntent().getExtras().getString(BILL_ID.getValue());
            serviceType = getIntent().getExtras().getInt(SERVICE_TYPE.getValue());
            getIntent().getExtras().clear();
        }
        initialize();
        setContentView(binding.getRoot());
    }

    private void initialize() {
        binding.fragmentServices.setOnClickListener(v ->
                binding.stepper.go(binding.stepper.getCurrentStep() + 1, true));
        getSupportFragmentManager().beginTransaction().add(binding.fragmentServices.getId(),
                ServiceIntroductionFragment.newInstance(serviceType)).commit();
    }

    @Override
    public void submitServices(ArrayList<Integer> selectedServicesId, ArrayList<String> selectedServicesTitle) {
        binding.stepper.go(1, true);
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentServices.getId(),
                ServiceFormFragment.newInstance(billId)).commit();
    }
}