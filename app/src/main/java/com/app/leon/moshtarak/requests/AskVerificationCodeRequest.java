package com.app.leon.moshtarak.requests;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.di.view_model.VerificationViewModel;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

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
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<VerificationViewModel> call = iAbfaService.askVerificationCode(verificationViewModel);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new VerificationCodeSuccessful(callback),
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
        showFailedMessage(t, context);
    }
}
