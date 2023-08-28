package com.leon.hamrah_abfa.fragments.contact_us;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.Context.CLIPBOARD_SERVICE;
import static com.leon.hamrah_abfa.enums.FragmentTags.WAITING;
import static com.leon.hamrah_abfa.utils.PermissionManager.checkCallPhonePermission;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.success;
import static com.leon.toast.RTLToast.warning;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.adapters.base_adapter.PhonebookAdapter;
import com.leon.hamrah_abfa.databinding.FragmentContactPhonebookBinding;
import com.leon.hamrah_abfa.fragments.dialog.WaitingFragment;
import com.leon.hamrah_abfa.requests.contact_us.GetTelRequest;

import java.util.ArrayList;

public class ContactPhonebookFragment extends Fragment implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, TextWatcher {
    private final ContactPhoneBook phonebook = new ContactPhoneBook();
    private final ActivityResultLauncher<String> requestCallPhonePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    success(requireContext(), getString(R.string.call_permission_granted)).show();
                } else {
                    error(requireContext(), getString(R.string.call_permission_unavailable)).show();
                }
            });
    private FragmentContactPhonebookBinding binding;
    private DialogFragment fragment;
    private PhonebookAdapter adapter;

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
        requestTel();
        binding.editTextSearch.addTextChangedListener(this);
    }

    private void requestTel() {
        boolean isOnline = new GetTelRequest(requireContext(), new GetTelRequest.ICallback() {

            @Override
            public void succeed(ArrayList<PhonebookViewModel> phonebook) {
                ContactPhonebookFragment.this.phonebook.telInfoDtos.addAll(phonebook);
                initializeGridView();
            }

            @Override
            public void changeUI(boolean done) {
                progressStatus(done);
            }
        }).request();
        progressStatus(isOnline);
    }

    private void progressStatus(boolean show) {
        if (show) {
            if (fragment == null) {
                fragment = WaitingFragment.newInstance();
                showFragmentDialogOnce(requireContext(), WAITING.getValue(), fragment);
            }
        } else {
            if (fragment != null) {
                fragment.dismiss();
                fragment = null;
            }
        }
    }

    private void initializeGridView() {
        adapter = new PhonebookAdapter(requireContext(), phonebook.telInfoDtos);
        binding.gridViewPhones.setAdapter(adapter);
        binding.gridViewPhones.setOnItemClickListener(this);
        binding.gridViewPhones.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (checkCallPhonePermission(requireActivity())) {
            call(phonebook.telInfoDtos.get(position).getNumber());
        } else {
            requestCallPhonePermissionLauncher.launch(CALL_PHONE);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(CLIPBOARD_SERVICE);
        final ClipData clip = ClipData.newPlainText("phonebook", phonebook.telInfoDtos.get(position).getNumber());
        clipboard.setPrimaryClip(clip);
        success(requireContext(), getString(R.string.saved_in_clipboard)).show();
        return true;
    }

    private void call(String phoneNumber) {
        final Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (binding.editTextSearch.getEditableText() == s) {
            if (s.toString().length() == 0) {
                adapter.filterList(phonebook.telInfoDtos);
                return;
            }
            filter(s.toString());
        }
    }

    private void filter(String text) {
        final ArrayList<PhonebookViewModel> phonebookTemp = new ArrayList<>();
        for (PhonebookViewModel phone : phonebook.telInfoDtos) {
            if (phone.getTitle().toLowerCase().contains(text.toLowerCase()))
                phonebookTemp.add(phone);
        }
        if (phonebookTemp.isEmpty()) {
            warning(requireContext(), R.string.not_found).show();
        } else {
            adapter.filterList(phonebookTemp);
        }
    }
}