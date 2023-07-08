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

public class VerifyReceivedCodeRequest {
    private final Context context;
    private final ICallback callback;
    private final PreLoginViewModel preLogin;

    public VerifyReceivedCodeRequest(Context context, PreLoginViewModel preLogin, ICallback callback) {
        this.context = context;
        this.preLogin = preLogin;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(false);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<PreLoginViewModel> call = iAbfaService.verifyCode(preLogin);
        return HttpClientWrapper.callHttpAsync(context, call, new VerifyCodeSuccessful(callback, context),
                new VerifyCodeIncomplete(context, callback), new VerifyCodeFailed(context, callback));
    }

    public interface ICallback {

        void succeed(String token, String failureMessage, boolean result);

        void changeUI(boolean done);
    }
}

class VerifyCodeSuccessful implements ICallbackSucceed<PreLoginViewModel> {
    private final VerifyReceivedCodeRequest.ICallback callback;
    private final Context context;

    public VerifyCodeSuccessful(VerifyReceivedCodeRequest.ICallback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    @Override
    public void executeCompleted(Response<PreLoginViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            if (response.body().isResult())
                callback.succeed(response.body().getToken(), response.body().getFailureMessage(), response.body().isResult());
            else warning(context, response.body().getFailureMessage()).show();
        }

    }
}

class VerifyCodeIncomplete implements ICallbackIncomplete<PreLoginViewModel> {
    private final Context context;
    private final VerifyReceivedCodeRequest.ICallback callback;

    public VerifyCodeIncomplete(Context context, VerifyReceivedCodeRequest.ICallback callback) {
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

class VerifyCodeFailed implements ICallbackFailure {
    private final Context context;
    private final VerifyReceivedCodeRequest.ICallback callback;

    public VerifyCodeFailed(Context context, VerifyReceivedCodeRequest.ICallback callback) {
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
