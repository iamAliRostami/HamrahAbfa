package com.leon.hamrah_abfa.requests;

import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;
import android.util.Log;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.di.view_model.VerificationViewModel;
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
    private final VerificationViewModel verificationViewModel;

    public AskVerificationCodeRequest(Context context, VerificationViewModel verificationViewModel, ICallback callback) {
        this.context = context;
        this.verificationViewModel = verificationViewModel;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(false);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<VerificationViewModel> call = iAbfaService.askVerificationCode(verificationViewModel);
        return HttpClientWrapper.callHttpAsync(context, call, new VerificationCodeSuccessful(callback),
                new VerificationCodeIncomplete(context, callback), new VerificationCodeFailed(context, callback));
    }

    public interface ICallback {
        void succeed(String id, long remainedSeconds);

        void changeUI(boolean done);
    }
}

class VerificationCodeSuccessful implements ICallbackSucceed<VerificationViewModel> {
    private final AskVerificationCodeRequest.ICallback callback;

    public VerificationCodeSuccessful(AskVerificationCodeRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<VerificationViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            callback.succeed(response.body().getVerificationId(), response.body().getRemainedSeconds());
        }

    }
}

class VerificationCodeIncomplete implements ICallbackIncomplete<VerificationViewModel> {
    private final Context context;
    private final AskVerificationCodeRequest.ICallback callback;

    public VerificationCodeIncomplete(Context context, AskVerificationCodeRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<VerificationViewModel> response) {
        callback.changeUI(true);
        //TODO
        warning(context, "dismissed").show();
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
        Log.e("error", t.toString());
        //TODO
        error(context, "failed").show();
    }
}
