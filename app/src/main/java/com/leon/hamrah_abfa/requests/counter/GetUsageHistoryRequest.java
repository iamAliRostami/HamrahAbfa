package com.leon.hamrah_abfa.requests.counter;

import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.hamrah_abfa.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.fragments.usage_history.Attempt;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetUsageHistoryRequest {
    private final Context context;
    private final ICallback callback;
    private final String id;

    public GetUsageHistoryRequest(Context context, ICallback callback, String id) {
        this.context = context;
        this.callback = callback;
        this.id = id;
    }

    public boolean request() {
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<Attempt> call = iAbfaService.getAttempts(id);
        callback.changeUI(true);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new UsageHistorySuccessful(callback),
                new UsageHistoryIncomplete(context, callback), new UsageHistoryFailed(context, callback));
    }

    public interface ICallback {
        void succeed(Attempt kardex);

        void changeUI(boolean show);
    }
}

class UsageHistorySuccessful implements ICallbackSucceed<Attempt> {
    private final GetUsageHistoryRequest.ICallback callback;

    public UsageHistorySuccessful(GetUsageHistoryRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<Attempt> response) {
        if (response.body() != null) {
            callback.succeed(response.body());
        }
        callback.changeUI(false);
    }
}

class UsageHistoryIncomplete implements ICallbackIncomplete<Attempt> {
    private final Context context;
    private final GetUsageHistoryRequest.ICallback callback;

    public UsageHistoryIncomplete(Context context, GetUsageHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<Attempt> response) {
        //TODO
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            warning(context, "dismissed").show();
        }
    }
}

class UsageHistoryFailed implements ICallbackFailure {
    private final Context context;
    private final GetUsageHistoryRequest.ICallback callback;

    public UsageHistoryFailed(Context context, GetUsageHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}