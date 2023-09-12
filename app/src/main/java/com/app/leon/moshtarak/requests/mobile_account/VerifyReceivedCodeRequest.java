package com.app.leon.moshtarak.requests.mobile_account;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.di.view_model.VerificationViewModel;
import com.app.leon.moshtarak.fragments.mobile.PreLoginViewModel;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VerifyReceivedCodeRequest {
    private final Context context;
    private final ICallback callback;
    private final VerificationViewModel verification;

    public VerifyReceivedCodeRequest(Context context, VerificationViewModel verification, ICallback callback) {
        this.context = context;
        this.verification = verification;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(false);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<PreLoginViewModel> call = iAbfaService.verifyCode(verification);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new VerifyCodeSuccessful(callback, context),
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
        if (response.body() != null) {
            if (response.body().getStatus() == 400) {
                warning(context, response.body().getMessage()).show();
            } else if (response.body().getStatus() == 500) {
                warning(context, R.string.server_error).show();
            }

        }
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
        showFailedMessage(t, context);
    }
}
