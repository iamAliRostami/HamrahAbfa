package com.leon.hamrah_abfa.base_items;

import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public abstract class BaseBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    protected abstract void initialize();

}
