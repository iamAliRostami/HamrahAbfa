package com.leon.hamrah_abfa.requests;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.hamrah_abfa.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.fragments.bottom_sheets.MobileHistory;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetMobileHistoryRequest {
    private final Context context;
    private final ICallback callback;

    public GetMobileHistoryRequest(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<MobileHistory> call = iAbfaService.mobileHistory();
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new MobileHistorySuccessful(callback),
                new MobileHistoryIncomplete(context, callback), new MobileHistoryFailed(context, callback));
    }

    public interface ICallback {
        void succeed(MobileHistory session);

        void changeUI(boolean show);
    }
}

class MobileHistorySuccessful implements ICallbackSucceed<MobileHistory> {
    private final GetMobileHistoryRequest.ICallback callback;

    public MobileHistorySuccessful(GetMobileHistoryRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<MobileHistory> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.succeed(response.body());
        }
    }
}

class MobileHistoryIncomplete implements ICallbackIncomplete<MobileHistory> {
    private final Context context;
    private final GetMobileHistoryRequest.ICallback callback;

    public MobileHistoryIncomplete(Context context, GetMobileHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<MobileHistory> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            //TODO
            warning(context, "dismissed").show();
        }
    }
}

class MobileHistoryFailed implements ICallbackFailure {
    private final Context context;
    private final GetMobileHistoryRequest.ICallback callback;

    public MobileHistoryFailed(Context context, GetMobileHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}