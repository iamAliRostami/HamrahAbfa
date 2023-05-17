package com.leon.hamrah_abfa.base_items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.leon.hamrah_abfa.R;

public abstract class BaseBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private View view;

    protected abstract View initializeBase(LayoutInflater inflater, ViewGroup container);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = initializeBase(inflater, container);
        initialize();
        return view;
    }

    private void initialize() {
        final ImageView imageViewArrow = view.findViewById(R.id.image_view_arrow_down);
        imageViewArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.image_view_arrow_down) {
            dismiss();
        }
    }
}
