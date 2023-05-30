package com.leon.hamrah_abfa.fragments.contact_us;

import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_FORBIDDEN_COMPLETE_FRAGMENT;
import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentContactForbiddenBaseBinding;

public class ContactForbiddenBaseFragment extends Fragment implements View.OnClickListener {
    private FragmentContactForbiddenBaseBinding binding;
    private ICallback callback;

    public ContactForbiddenBaseFragment() {
    }

    public static ContactForbiddenBaseFragment newInstance() {
        return new ContactForbiddenBaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactForbiddenBaseBinding.inflate(inflater, container, false);
        callback.getForbiddenViewModel().resetViewModel();
        binding.setViewModel(callback.getForbiddenViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_next) {
            if (callback.getForbiddenViewModel().getDescription() == null ||
                    callback.getForbiddenViewModel().getDescription().isEmpty()) {
                binding.editTextDescription.setError(getString(R.string.enter_forbidden_description));
                binding.editTextDescription.requestFocus();
                warning(requireContext(), R.string.enter_forbidden_description).show();
                return;
            }
            callback.displayView(CONTACT_FORBIDDEN_COMPLETE_FRAGMENT);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        void displayView(int position);

        ForbiddenViewModel getForbiddenViewModel();
    }
}