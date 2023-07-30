package com.leon.hamrah_abfa.fragments.contact_us;

import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_FORBIDDEN_COMPLETE_FRAGMENT;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentContactForbiddenInfoBinding;

public class ContactForbiddenInfoFragment extends Fragment implements View.OnClickListener {
    private FragmentContactForbiddenInfoBinding binding;
    private ICallback callback;

    public ContactForbiddenInfoFragment() {
    }

    public static ContactForbiddenInfoFragment newInstance() {
        return new ContactForbiddenInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactForbiddenInfoBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getForbiddenViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonNext.setOnClickListener(this);
        binding.buttonPrevious.setOnClickListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_next) {
            callback.displayView(CONTACT_FORBIDDEN_COMPLETE_FRAGMENT);
        } else if (id == R.id.button_previous) {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    public interface ICallback {
        void displayView(int position);

        ForbiddenViewModel getForbiddenViewModel();
    }
}