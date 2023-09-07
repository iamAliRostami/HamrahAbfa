package com.app.leon.moshtarak.base_items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.leon.moshtarak.R;

public abstract class BaseLastBillFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ImageView imageViewArrow;

    protected abstract View initializeBase(LayoutInflater inflater, ViewGroup container);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = initializeBase(inflater, container);
        initialize();
        return view;
    }

    private void initialize() {
        imageViewArrow = view.findViewById(R.id.image_view_arrow);
        imageViewArrow.setImageResource(R.drawable.arrow_down);
        imageViewArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.image_view_arrow) {
            final RelativeLayout relativeLayout = view.findViewById(R.id.relative_layout_detail);
            if (relativeLayout.getVisibility() == View.VISIBLE) {
                imageViewArrow.setImageResource(R.drawable.arrow_down);
                relativeLayout.setVisibility(View.GONE);
            } else {
                imageViewArrow.setImageResource(R.drawable.arrow_up);
                relativeLayout.setVisibility(View.VISIBLE);
            }

        }
    }
}
