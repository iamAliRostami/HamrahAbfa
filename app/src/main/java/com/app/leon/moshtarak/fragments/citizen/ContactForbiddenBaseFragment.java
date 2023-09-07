package com.app.leon.moshtarak.fragments.citizen;

import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.databinding.FragmentContactForbiddenBaseBinding;
import com.app.leon.moshtarak.helpers.Constants;

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
            if (callback.getForbiddenViewModel().getTedadVahed() == null ||
                    callback.getForbiddenViewModel().getTedadVahed().isEmpty())
                callback.getForbiddenViewModel().setTedadVahed("");
            callback.displayView(Constants.CONTACT_FORBIDDEN_DESCRIPTION_FRAGMENT);
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