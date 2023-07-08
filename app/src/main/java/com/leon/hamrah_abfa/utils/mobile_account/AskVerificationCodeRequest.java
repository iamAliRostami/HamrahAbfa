package com.leon.hamrah_abfa.utils.mobile_account;

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

public class AskVerificationCodeRequest {
    private final Context context;
    private final ICallback callback;
    private final PreLoginViewModel preLogin;

    public AskVerificationCodeRequest(Context context, PreLoginViewModel preLogin, ICallback callback) {
        this.context = context;
        this.preLogin = preLogin;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(false);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<PreLoginViewModel> call = iAbfaService.preLogin(preLogin);
        return HttpClientWrapper.callHttpAsync(context, call, new AskVerificationCodeSuccessful(callback),
                new AskVerificationCodeIncomplete(context, callback), new AskVerificationCodeFailed(context, callback));
    }

    public interface ICallback {
        void succeed(String id, long remainedSeconds);

        void changeUI(boolean done);
    }
}

class AskVerificationCodeSuccessful implements ICallbackSucceed<PreLoginViewModel> {
    private final AskVerificationCodeRequest.ICallback callback;

    public AskVerificationCodeSuccessful(AskVerificationCodeRequest.ICallback callback) {
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

class AskVerificationCodeIncomplete implements ICallbackIncomplete<PreLoginViewModel> {
    private final Context context;
    private final AskVerificationCodeRequest.ICallback callback;

    public AskVerificationCodeIncomplete(Context context, AskVerificationCodeRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<PreLoginViewModel> response) {
        callback.changeUI(true);
        //TODO
        warning(context, "dismissed").show();
    }
}

class AskVerificationCodeFailed implements ICallbackFailure {
    private final Context context;
    private final AskVerificationCodeRequest.ICallback callback;

    public AskVerificationCodeFailed(Context context, AskVerificationCodeRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(true);
        //TODO
        error(context, "failed").show();
    }
}
