package com.leon.hamrah_abfa.utils.mobile_submit;

import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.fragments.mobile.PreLoginViewModel;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PreLoginRequest {
    private final Context context;
    private final PreLoginViewModel preLogin;
    private final ICallback callback;

    public PreLoginRequest(Context context, PreLoginViewModel preLogin, ICallback callback) {
        this.context = context;
        this.preLogin = preLogin;
        this.callback = callback;
    }

    public void request() {
        callback.changeUI(false);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<PreLoginViewModel> call = iAbfaService.preLogin(preLogin);
        HttpClientWrapper.callHttpAsync(context, call, new PreLoginSuccessful(callback),
                new PreLoginIncomplete(context, callback), new PreLoginFailed(context, callback));
    }

    public interface ICallback {
        void succeed(String id, long remainedSeconds);

        void changeUI(boolean done);
    }
}

class PreLoginSuccessful implements ICallbackSucceed<PreLoginViewModel> {
    private final PreLoginRequest.ICallback callback;

    public PreLoginSuccessful(PreLoginRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<PreLoginViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            callback.succeed(response.body().getId(), response.body().getRemainedSeconds());
        }

    }
}

class PreLoginIncomplete implements ICallbackIncomplete<PreLoginViewModel> {
    private final Context context;
    private final PreLoginRequest.ICallback callback;

    public PreLoginIncomplete(Context context, PreLoginRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<PreLoginViewModel> response) {
        callback.changeUI(true);
        warning(context, "dismissed").show();
    }
}

class PreLoginFailed implements ICallbackFailure {
    private final Context context;
    private final PreLoginRequest.ICallback callback;

    public PreLoginFailed(Context context, PreLoginRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(true);
        error(context, "failed").show();
    }
}
