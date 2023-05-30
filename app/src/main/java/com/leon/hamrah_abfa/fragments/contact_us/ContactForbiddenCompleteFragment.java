package com.leon.hamrah_abfa.fragments.contact_us;

import static com.leon.hamrah_abfa.helpers.Constants.CONTACT_FORBIDDEN_FRAGMENT;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentContactForbiddenCompleteBinding;

public class ContactForbiddenCompleteFragment extends Fragment implements View.OnClickListener {
    private ICallback callback;
    private FragmentContactForbiddenCompleteBinding binding;

    public ContactForbiddenCompleteFragment() {
    }

    public static ContactForbiddenCompleteFragment newInstance() {
        return new ContactForbiddenCompleteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactForbiddenCompleteBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonConfirm.setOnClickListener(this);
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
        if (id == R.id.button_confirm) {
//            callback.displayView();
        } else if (id == R.id.button_previous) {
            requireActivity().getSupportFragmentManager().popBackStack();
        } else if (id == R.id.image_view_current_location) {

        }
    }

    public interface ICallback {
        void displayView(int position);
    }
}