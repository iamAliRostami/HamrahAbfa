package com.app.leon.moshtarak.fragments.follow_request;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.base_items.BaseBottomSheetFragment;
import com.app.leon.moshtarak.databinding.FragmentFollowRequestTrackBinding;

public class FollowRequestTrackFragment extends BaseBottomSheetFragment implements TextWatcher {
    private FragmentFollowRequestTrackBinding binding;
    private ICallback callback;

    public FollowRequestTrackFragment() {
    }

    public static FollowRequestTrackFragment newInstance() {
        return new FollowRequestTrackFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initializeBase(LayoutInflater inflater, ViewGroup container) {
        binding = FragmentFollowRequestTrackBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.editTextTrackNumber.setText(callback.getFilterValue());
        binding.editTextTrackNumber.addTextChangedListener(this);
        binding.buttonSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.button_search) {
            dismiss();
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        callback.setFilterValue(editable.toString());
        callback.filter();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
            callback = (ICallback) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface ICallback {
        void filter();

        String getFilterValue();

        void setFilterValue(String filterValue);
    }
}