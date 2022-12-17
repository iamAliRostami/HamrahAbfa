package com.leon.hamrah_abfa.di.view_model;


import static com.leon.hamrah_abfa.enums.ProgressType.SHOW;
import static com.leon.hamrah_abfa.enums.ProgressType.SHOW_CANCELABLE;
import static com.leon.hamrah_abfa.enums.ProgressType.SHOW_CANCELABLE_REDIRECT;
import static com.leon.hamrah_abfa.helpers.MyApplication.getApplicationComponent;
import static com.leon.hamrah_abfa.utils.PermissionManager.isNetworkAvailable;
import static com.leon.hamrah_abfa.utils.toast.CustomToast.error;
import static com.leon.hamrah_abfa.utils.toast.CustomToast.warning;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.infrastructure.ICallback;
import com.leon.hamrah_abfa.infrastructure.ICallbackError;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpClientWrapper {
    public static Call call;
    public static boolean cancel;

    @SuppressLint("CheckResult")
    public static <T> void callHttpAsync(Call<T> call, final int progressType, final Context context,
                                         final ICallback<T> callback, final ICallbackIncomplete<T> incomplete,
                                         final ICallbackError error) {
        cancel = false;
        final CustomProgressModel progress = getApplicationComponent().CustomProgressModel();
        try {
            if (progressType == SHOW.getValue()) {
                progress.show(context, context.getString(R.string.waiting));
            } else if (progressType == SHOW_CANCELABLE.getValue()) {
                progress.show(context, context.getString(R.string.waiting), true);
            } else if (progressType == SHOW_CANCELABLE_REDIRECT.getValue()) {
                progress.show(context, context.getString(R.string.waiting), true);
            }
        } catch (Exception e) {
            error(context, e.getMessage(), Toast.LENGTH_LONG);
        }

        if (isNetworkAvailable(context)) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                    if (!cancel) {
                        if (progress.getDialog() != null)
                            try {
                                progress.getDialog().dismiss();
                            } catch (Exception e) {
                                error(context, e.getMessage(), Toast.LENGTH_LONG);
                            }
                        if (response.isSuccessful()) {
                            callback.execute(response);
                        } else {
                            ((Activity) context).runOnUiThread(() -> incomplete.executeIncomplete(response));
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                    ((Activity) context).runOnUiThread(() -> error.executeError(t));
                    if (progress.getDialog() != null)
                        try {
                            progress.getDialog().dismiss();
                        } catch (Exception e) {
                            error(context, e.getMessage(), Toast.LENGTH_LONG);
                        }
                }
            });
            HttpClientWrapper.call = call;
        } else {
            if (progress.getDialog() != null)
                try {
                    progress.getDialog().dismiss();
                } catch (Exception e) {
                    error(context, e.getMessage(), Toast.LENGTH_LONG);
                }
            warning(context, R.string.turn_internet_on);
        }
    }
}