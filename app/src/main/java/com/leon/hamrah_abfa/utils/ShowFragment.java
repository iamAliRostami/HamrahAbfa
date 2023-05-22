package com.leon.hamrah_abfa.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class ShowFragment {
    public static void showFragmentDialogOnce(Context context, String tag, DialogFragment dialogFragment) {
        final FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        final Fragment prev = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentByTag(tag);
        if (prev == null) {
            ft.addToBackStack(null);
            dialogFragment.show(ft, tag);
        }
    }

    public static void replaceFragment(Context context, int id, Fragment fragment) {
        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(id, fragment).commit();
    }
}