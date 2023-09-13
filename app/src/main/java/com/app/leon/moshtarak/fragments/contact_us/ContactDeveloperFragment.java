package com.app.leon.moshtarak.fragments.contact_us;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.Context.CLIPBOARD_SERVICE;
import static com.app.leon.moshtarak.utils.PermissionManager.checkCallPhonePermission;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.success;

import android.content.ClipData;
import android.content.ClipboardManager;
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

import com.app.leon.moshtarak.BuildConfig;
import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.databinding.FragmentContactDeveloperBinding;

public class ContactDeveloperFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    public ContactDeveloperFragment() {
    }

    public static ContactDeveloperFragment newInstance() {
        return new ContactDeveloperFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentContactDeveloperBinding binding = FragmentContactDeveloperBinding.inflate(inflater, container, false);
        binding.textViewVersion.setText(String.format(getString(R.string.version), BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE));
        binding.textViewEmail.setOnClickListener(this);
        binding.textViewPhone.setOnClickListener(this);
        binding.textViewPhone.setOnLongClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.text_view_phone) {
            if (checkCallPhonePermission(requireActivity())) {
                call();
            } else {
                requestCallPhonePermissionLauncher.launch(CALL_PHONE);
            }
        } else if (id == R.id.text_view_email) {
            email();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int id = v.getId();
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(CLIPBOARD_SERVICE);
        if (id == R.id.text_view_phone) {
            ClipData clip = ClipData.newPlainText("phone", getString(R.string.phone));
            clipboard.setPrimaryClip(clip);
        } else if (id == R.id.text_view_email) {
            ClipData clip = ClipData.newPlainText("email", getString(R.string.email));
            clipboard.setPrimaryClip(clip);
        }
        success(requireContext(), getString(R.string.saved_in_clipboard)).show();
        return false;
    }

    private void call() {
        final Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + getString(R.string.company_phone)));
        startActivity(intent);
    }

    private void email() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.company_email)});
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
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