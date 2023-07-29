package com.leon.hamrah_abfa.requests.mobile_account;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.di.view_model.VerificationViewModel;
import com.leon.hamrah_abfa.fragments.mobile.PreLoginViewModel;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AskVerificationCodeRequest {
    private final Context context;
    private final ICallback callback;
    private final VerificationViewModel verification;

    public AskVerificationCodeRequest(Context context, VerificationViewModel verification, ICallback callback) {
        this.context = context;
        this.verification = verification;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(false);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<PreLoginViewModel> call = iAbfaService.preLogin(verification);
        return callHttpAsync(context, call, new VerificationCodeSuccessful(callback),
                new VerificationCodeIncomplete(context, callback), new VerificationCodeFailed(context, callback));
    }

    public interface ICallback {
        void succeed(String id, long remainedSeconds);

        void changeUI(boolean done);
    }
}

class VerificationCodeSuccessful implements ICallbackSucceed<PreLoginViewModel> {
    private final AskVerificationCodeRequest.ICallback callback;

    public VerificationCodeSuccessful(AskVerificationCodeRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<PreLoginViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            callback.succeed(response.body().getVerificationId(), response.body().getRemainedSeconds());
        }

    }
}

class VerificationCodeIncomplete implements ICallbackIncomplete<PreLoginViewModel> {
    private final Context context;
    private final AskVerificationCodeRequest.ICallback callback;

    public VerificationCodeIncomplete(Context context, AskVerificationCodeRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<PreLoginViewModel> response) {
        callback.changeUI(true);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            //TODO
            warning(context, "dismissed").show();
        }
    }
}

class VerificationCodeFailed implements ICallbackFailure {
    private final Context context;
    private final AskVerificationCodeRequest.ICallback callback;

    public VerificationCodeFailed(Context context, AskVerificationCodeRequest.ICallback callback) {
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
