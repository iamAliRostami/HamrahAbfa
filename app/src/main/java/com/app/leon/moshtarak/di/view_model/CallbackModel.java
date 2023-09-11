package com.app.leon.moshtarak.di.view_model;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.app.leon.moshtarak.infrastructure.ICallback;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallbackModel<T> implements Callback<T> {
    private Callback<T> callback;
    private boolean canceled = false;

    public CallbackModel(Callback<T> callback) {
        this.callback = callback;
    }

    public CallbackModel(Context context, ICallbackSucceed<T> succeed, ICallbackIncomplete<T> incomplete, ICallbackFailure error) {
        this.callback = new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful()) {
                    ((Activity) context).runOnUiThread(() -> succeed.executeCompleted(response));
                } else {
                    ((Activity) context).runOnUiThread(() -> incomplete.executeDismissed(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                cancel();
                ((Activity) context).runOnUiThread(() -> error.executeFailed(t));
            }
        };
    }

    public CallbackModel(Context context, ICallback<T> callback) {
        this.callback = new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful()) {
                    ((Activity) context).runOnUiThread(() -> callback.executeCompleted(response));
                } else {
                    ((Activity) context).runOnUiThread(() -> callback.executeDismissed(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                cancel();
                ((Activity) context).runOnUiThread(() -> callback.executeFailed(t));
            }
        };
    }

    public void cancel() {
        canceled = true;
        callback = null;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (!canceled)
            callback.onResponse(call, response);
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        if (!canceled)
            callback.onFailure(call, t);
    }
}
