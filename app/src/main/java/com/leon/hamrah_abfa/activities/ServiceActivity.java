package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.SERVICE_TYPE;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.ActivityServiceBinding;
import com.leon.hamrah_abfa.fragments.ServicesLocationDialogFragment;
import com.leon.hamrah_abfa.fragments.bottom_sheets.ServicesLocationFragment;
import com.leon.hamrah_abfa.fragments.services.ServiceFormFragment;
import com.leon.hamrah_abfa.fragments.services.ServiceIntroductionFragment;
import com.leon.hamrah_abfa.fragments.services.ServicesViewModel;
import com.leon.hamrah_abfa.adapters.base_adapter.ServicesIntroductionBaseAdapter;
import com.leon.toast.RTLToast;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity implements ServiceIntroductionFragment.ICallback,
        ServiceFormFragment.ICallback, ServicesLocationFragment.ICallback, ServicesLocationDialogFragment.ICallback {
    private ServicesViewModel viewModel;
    private long lastClickTime = 0;
    private ActivityServiceBinding binding;
    private ServicesIntroductionBaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            viewModel = new ServicesViewModel(this, getIntent().getExtras().getInt(SERVICE_TYPE.getValue()),
                    getIntent().getExtras().getString(BILL_ID.getValue()));

            getIntent().getExtras().clear();
        }
        initialize();
        setContentView(binding.getRoot());
    }

    private void initialize() {
        binding.fragmentServices.setOnClickListener(v ->
                binding.stepper.go(binding.stepper.getCurrentStep() + 1, true));
        getSupportFragmentManager().beginTransaction().add(binding.fragmentServices.getId(),
                ServiceIntroductionFragment.newInstance()).commit();
    }

    @Override
    public void submitServices(ArrayList<Integer> selectedServicesId, ArrayList<String> selectedServicesTitle) {
        binding.stepper.go(1, true);
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentServices.getId(),
                ServiceFormFragment.newInstance()).commit();
    }

    @Override
    public ServicesIntroductionBaseAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void setAdapter(ServicesIntroductionBaseAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public ServicesViewModel getServicesViewModel() {
        return viewModel;
    }

    @Override
    public void submitUserInfo() {

    }

    @Override
    public void goServices() {
        binding.stepper.go(binding.stepper.getCurrentStep() - 1, true);
        getSupportFragmentManager().beginTransaction().replace(binding.fragmentServices.getId(),
                ServiceIntroductionFragment.newInstance()).commit();
    }

    @Override
    public void setLocation(Bitmap bitmapLocation, GeoPoint point) {
        viewModel.setPoint(point);
        viewModel.setBitmapLocation(bitmapLocation.copy(Bitmap.Config.ARGB_8888, true));
        final Fragment fragment = getSupportFragmentManager().findFragmentById(binding.fragmentServices.getId());
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.detach(fragment);
        transaction.commit();
        final FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
        transaction1.attach(fragment);
        transaction1.commit();

//        getSupportFragmentManager().beginTransaction().replace(binding.fragmentServices.getId(),
//                ServiceFormFragment.newInstance()).commit();
    }

    @Override
    public void onBackPressed() {
        if (SystemClock.elapsedRealtime() - lastClickTime < 2000) super.onBackPressed();
        RTLToast.info(this, getString(R.string.return_by_press_again)).show();
        lastClickTime = SystemClock.elapsedRealtime();
    }

}