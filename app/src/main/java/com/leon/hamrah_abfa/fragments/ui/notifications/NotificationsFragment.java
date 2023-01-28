package com.leon.hamrah_abfa.fragments.ui.notifications;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.leon.hamrah_abfa.databinding.FragmentNotificationsBinding;


public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);

        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), binding.textNotifications::setText);

        initialize();
        return binding.getRoot();
    }

    private void initialize() {
        binding.imageViewAnimation.playAnimation();
        binding.imageViewAnimation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
                Log.e("here","onAnimationStart");
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                Log.e("here","onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
                Log.e("here","onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
                Log.e("here","onAnimationRepeat");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}