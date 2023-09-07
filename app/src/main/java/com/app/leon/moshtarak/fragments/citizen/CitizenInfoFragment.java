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
import com.app.leon.moshtarak.databinding.FragmentCitizenInfoBinding;
import com.app.leon.moshtarak.helpers.Constants;

public class CitizenInfoFragment extends Fragment implements View.OnClickListener {
    private FragmentCitizenInfoBinding binding;
    private ICallback callback;

    public CitizenInfoFragment() {
    }

    public static CitizenInfoFragment newInstance() {
        return new CitizenInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCitizenInfoBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getForbiddenViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {

        binding.buttonNext.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_next) {
            if (checkInputs()) {
                if (callback.getForbiddenViewModel().getPostalCode() == null ||
                        callback.getForbiddenViewModel().getPostalCode().isEmpty())
                    callback.getForbiddenViewModel().setPostalCode("");
                if (callback.getForbiddenViewModel().getNextEshterak() == null ||
                        callback.getForbiddenViewModel().getNextEshterak().isEmpty())
                    callback.getForbiddenViewModel().setNextEshterak("");
                if (callback.getForbiddenViewModel().getPreEshterak() == null ||
                        callback.getForbiddenViewModel().getPreEshterak().isEmpty())
                    callback.getForbiddenViewModel().setPreEshterak("");
                callback.displayView(Constants.CITIZEN_COMPLETE_FRAGMENT);
            }
        } else if (id == R.id.button_previous) {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private boolean checkInputs() {
        if (callback.getForbiddenViewModel().getPostalCode() != null &&
                !callback.getForbiddenViewModel().getPostalCode().isEmpty() &&
                callback.getForbiddenViewModel().getPostalCode().length() < 10) {
            warning(requireContext(), R.string.incorrect_postal_code_format).show();
            binding.editTextPostal.setError(getString(R.string.incorrect_postal_code_format));
            binding.editTextPostal.requestFocus();
            return false;
        } else if (callback.getForbiddenViewModel().getPreEshterak() != null &&
                !callback.getForbiddenViewModel().getPreEshterak().isEmpty() &&
                callback.getForbiddenViewModel().getPreEshterak().length() < 8) {
            warning(requireContext(), R.string.incorrect_esherak).show();
            binding.editTextPre.setError(getString(R.string.incorrect_esherak));
            binding.editTextPre.requestFocus();
            return false;
        } else if (callback.getForbiddenViewModel().getNextEshterak() != null &&
                !callback.getForbiddenViewModel().getNextEshterak().isEmpty() &&
                callback.getForbiddenViewModel().getNextEshterak().length() < 8) {
            warning(requireContext(), R.string.incorrect_esherak).show();
            binding.editTextNext.setError(getString(R.string.incorrect_esherak));
            binding.editTextNext.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
            callback = (ICallback) context;
    }

    public interface ICallback {
        void displayView(int position);

        ForbiddenViewModel getForbiddenViewModel();
    }
}