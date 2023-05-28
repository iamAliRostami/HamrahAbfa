package com.leon.hamrah_abfa.fragments.contact_us;

import static android.Manifest.permission.CALL_PHONE;
import static com.leon.hamrah_abfa.utils.PermissionManager.checkCallPhonePermission;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.success;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.PhonebookAdapter;
import com.leon.hamrah_abfa.databinding.FragmentContactPhonebookBinding;

import java.util.ArrayList;

public class ContactPhonebookFragment extends Fragment implements AdapterView.OnItemClickListener {
    private FragmentContactPhonebookBinding binding;
    private final ArrayList<PhonebookViewModel> phonebook = new ArrayList<>();

    public ContactPhonebookFragment() {
    }

    public static ContactPhonebookFragment newInstance() {
        return new ContactPhonebookFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactPhonebookBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        initializeGridView();
//        requestCallPhonePermissionLauncher.launch(CALL_PHONE);
//        askForCallPermission();
    }

    private void initializeGridView() {
        phonebook.add(new PhonebookViewModel("مدیر شماره یک", "03133333333"));
        phonebook.add(new PhonebookViewModel("مدیر شماره دو", "03133333333"));
        phonebook.add(new PhonebookViewModel("مدیر شماره سه", "03133333333"));
        phonebook.add(new PhonebookViewModel("مدیر شماره چهار", "03133333333"));
        phonebook.add(new PhonebookViewModel("مدیر شماره پنج", "03133333333"));
        phonebook.add(new PhonebookViewModel("مدیر شماره شش", "03133333333"));
        phonebook.add(new PhonebookViewModel("مدیر شماره هفت", "03133333333"));
        phonebook.add(new PhonebookViewModel("مدیر شماره هشت", "03133333333"));
        final PhonebookAdapter adapter = new PhonebookAdapter(requireContext(), phonebook);
        binding.gridViewPhones.setAdapter(adapter);
        binding.gridViewPhones.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (checkCallPhonePermission(requireActivity())) {
            call(phonebook.get(position).getPhoneNumber());
        } else {
            requestCallPhonePermissionLauncher.launch(CALL_PHONE);
        }
    }

    private void call(String phoneNumber) {
        final Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
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