package com.leon.hamrah_abfa.activities;

import static com.leon.hamrah_abfa.enums.BundleEnum.BILL_ID;
import static com.leon.hamrah_abfa.enums.BundleEnum.SERVICE_TYPE;
import static com.leon.hamrah_abfa.enums.BundleEnum.UUID;
import static com.leon.hamrah_abfa.enums.FragmentTags.REQUEST_DONE;
import static com.leon.hamrah_abfa.helpers.Constants.SERVICE_FORM_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.SERVICE_INTRODUCTION_FRAGMENT;
import static com.leon.hamrah_abfa.helpers.Constants.SERVICE_SUBMIT_INFORMATION_FRAGMENT;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.ServicesIntroductionBaseAdapter;
import com.leon.hamrah_abfa.base_items.BaseActivity;
import com.leon.hamrah_abfa.databinding.ActivityServiceBinding;
import com.leon.hamrah_abfa.fragments.dialog.ServicesLocationDialogFragment;
import com.leon.hamrah_abfa.fragments.dialog.TrackDoneRequestFragment;
import com.leon.hamrah_abfa.fragments.services.ServicesMapFragment;
import com.leon.hamrah_abfa.fragments.services.ServicesViewModel;
import com.leon.hamrah_abfa.fragments.services.after.ServiceFormFragment;
import com.leon.hamrah_abfa.fragments.services.after.ServiceIntroductionFragment;
import com.leon.hamrah_abfa.fragments.services.after.ServiceSubmitInformationFragment;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class ServiceAbActivity extends BaseActivity implements ServiceIntroductionFragment.ICallback,
        ServiceSubmitInformationFragment.ICallback, ServicesLocationDialogFragment.ICallback,
        ServiceFormFragment.ICallback, ServicesMapFragment.ICallback {
    private ServicesViewModel viewModel;
    private ActivityServiceBinding binding;
    private ServicesIntroductionBaseAdapter adapter;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void initialize() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivityServiceBinding.inflate(getLayoutInflater());
        if (getIntent().getExtras() != null) {
            viewModel = new ServicesViewModel(this,
                    getIntent().getExtras().getInt(SERVICE_TYPE.getValue()),
                    getIntent().getExtras().getString(BILL_ID.getValue()),
                    getIntent().getExtras().getString(UUID.getValue()));
            getIntent().getExtras().clear();
        }
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction().add(binding.fragmentServices.getId(),
                ServiceIntroductionFragment.newInstance()).commit();
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
    protected String getExitMessage() {
        return getString(R.string.return_by_press_again);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void displayView(int position, boolean next) {
        if (position == SERVICE_INTRODUCTION_FRAGMENT) {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentServices.getId(),
                    ServiceIntroductionFragment.newInstance()).commit();
        } else if (position == SERVICE_FORM_FRAGMENT) {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentServices.getId(),
                    ServiceFormFragment.newInstance()).commit();
        } else if (position == SERVICE_SUBMIT_INFORMATION_FRAGMENT) {
            getSupportFragmentManager().beginTransaction().replace(binding.fragmentServices.getId(),
                    ServiceSubmitInformationFragment.newInstance()).commit();
        }
        if (next)
            binding.stepper.go(binding.stepper.getCurrentStep() + 1, true);
        else binding.stepper.go(binding.stepper.getCurrentStep() - 1, true);
    }

    @Override
    public void setServices(ArrayList<Integer> selectedServicesId, ArrayList<String> selectedServicesTitle) {
        viewModel.setSelectedServicesString(selectedServicesTitle);
        viewModel.setSelectedServices(selectedServicesId);
    }

    @Override
    public void submitInformation(String trackNumber) {
        showFragmentDialogOnce(this, REQUEST_DONE.getValue(),
                TrackDoneRequestFragment.newInstance(trackNumber, getString(R.string.main_page),
                        new TrackDoneRequestFragment.IClickListener() {
                            @Override
                            public void yes(DialogFragment dialogFragment) {
                                finish();
                            }

                            @Override
                            public void no(DialogFragment dialogFragment) {
                            }
                        }));
    }

    @Override
    public void setLocation(Bitmap bitmapLocation, GeoPoint point) {
        viewModel.setPoint(point);
        viewModel.setX(String.valueOf(point.getLongitude()));
        viewModel.setY(String.valueOf(point.getLatitude()));
        viewModel.setBitmapLocation(bitmapLocation.copy(Bitmap.Config.ARGB_8888, true));
        //TODO
        final Fragment fragment = getSupportFragmentManager().findFragmentById(binding.fragmentServices.getId());
        if (fragment != null) {
            final FragmentTransaction transactionCurrent = getSupportFragmentManager().beginTransaction();
            transactionCurrent.detach(fragment).commit();
            final FragmentTransaction transactionNext = getSupportFragmentManager().beginTransaction();
            transactionNext.attach(fragment).commit();
        }
    }
}