package com.leon.hamrah_abfa.utils.mobile_submit;

import android.content.Context;

import androidx.annotation.NonNull;

import com.leon.hamrah_abfa.di.view_model.CallbackModel;
import com.leon.hamrah_abfa.infrastructure.ICallback;

import retrofit2.Call;
import retrofit2.Response;

public class PreLogin1<T> implements ICallback<T> {
    private final Context context;

    public PreLogin1(Context context) {
        this.context = context;
    }

    void test() {
        CallbackModel<T> callback = new CallbackModel<T>(context, this);
    }

    @Override
    public void executeFailed(Throwable t) {

    }

    @Override
    public void executeDismissed(Response<T> response) {

    }

    @Override
    public void executeCompleted(Response<T> response) {

    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {

    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {

    }
}
