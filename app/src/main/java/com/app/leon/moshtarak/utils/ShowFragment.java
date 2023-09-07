package com.app.leon.moshtarak.utils;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
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

    public static void setFragment(Context context, int id, Fragment fragment) {
        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(id, fragment).commit();
    }

    public static void addFragment(Context context, int id, Fragment fragment) {
        final FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction().add(id, fragment).commit();
    }

    public static void replaceFragment(Context context, int id, Fragment fragment) {
        final FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(id, fragment).addToBackStack(null).commit();
    }

    public static void dismissDialog(Context context, String tag) {
        Fragment prev = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }
}