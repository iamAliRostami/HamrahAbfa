package com.leon.hamrah_abfa.fragments.ui.incident;

import static android.Manifest.permission.CALL_PHONE;
import static com.leon.hamrah_abfa.utils.PermissionManager.checkCallPhonePermission;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.success;
import static com.leon.toast.RTLToast.warning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.activities.IncidentActivity;
import com.leon.hamrah_abfa.databinding.FragmentIncidentBinding;

public class IncidentFragment extends Fragment implements View.OnClickListener {
    private FragmentIncidentBinding binding;

    public IncidentFragment() {
    }

    public static IncidentFragment newInstance() {
        return new IncidentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIncidentBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonStart.setOnClickListener(this);
        binding.buttonCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_start) {
//            Intent intent = new Intent(requireContext(), IncidentActivity.class);
//            startActivity(intent);
            warning(requireContext(), R.string.will_be_ok_soon).show();
        } else if (id == R.id.button_call) {
            if (checkCallPhonePermission(requireActivity())) {
                call();
            } else {
                requestCallPhonePermissionLauncher.launch(CALL_PHONE);
            }
        }
    }

    private void call() {
        final Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + 122));
        startActivity(intent);
    }

    private final ActivityResultLauncher<String> requestCallPhonePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    success(requireContext(), getString(R.string.call_permission_granted)).show();
                } else {
                    error(requireContext(), getString(R.string.call_permission_unavailable)).show();
                }
            });
}