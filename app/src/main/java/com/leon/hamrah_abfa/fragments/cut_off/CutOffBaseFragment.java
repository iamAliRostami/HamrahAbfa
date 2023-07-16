package com.leon.hamrah_abfa.fragments.cut_off;

import static com.leon.hamrah_abfa.enums.FragmentTags.REQUEST_DONE;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.warning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.databinding.FragmentCutOffBaseBinding;
import com.leon.hamrah_abfa.fragments.dialog.TrackDoneRequestFragment;

public class CutOffBaseFragment extends Fragment implements View.OnClickListener {
    private FragmentCutOffBaseBinding binding;
    private ICallback callback;

    public CutOffBaseFragment() {
    }

    public static CutOffBaseFragment newInstance() {
        return new CutOffBaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCutOffBaseBinding.inflate(inflater, container, false);
        binding.setViewModel(callback.getViewModel());
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_submit) {
            if (callback.getViewModel().getDescription() == null ||
                    callback.getViewModel().getDescription().isEmpty()) {
                binding.editTextDescription.setError(getString(R.string.enter_description));
                binding.editTextDescription.requestFocus();
                warning(requireContext(), R.string.enter_description).show();
            } else confirmCode();
        }
    }

    private void confirmCode() {
        showFragmentDialogOnce(requireContext(), REQUEST_DONE.getValue(),
                TrackDoneRequestFragment.newInstance("123456", getString(R.string.return_home),
                        new TrackDoneRequestFragment.IClickListener() {
                            @Override
                            public void yes(DialogFragment dialogFragment) {
                                requireActivity().finish();
                            }

                            @Override
                            public void no(DialogFragment dialogFragment) {

                            }
                        }));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) callback = (ICallback) context;
    }

    public interface ICallback {
        CutOffViewModel getViewModel();
    }
}