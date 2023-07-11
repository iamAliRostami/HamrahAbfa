package com.leon.hamrah_abfa.requests;

import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;
import android.util.Log;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.fragments.checkout.Kardex;
import com.leon.hamrah_abfa.fragments.usage_history.Attempt;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;

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
        callback.changeUI(true);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<Attempt> call = iAbfaService.getAttempts(id);
        return HttpClientWrapper.callHttpAsync(context, call, new GetUsageHistorySuccessful(callback),
                new GetUsageHistoryIncomplete(context, callback), new GetUsageHistoryFailed(context, callback));
    }

    public interface ICallback {
        void succeed(Attempt kardex);

        void changeUI(boolean show);
    }
}

class GetUsageHistorySuccessful implements ICallbackSucceed<Attempt> {
    private final GetUsageHistoryRequest.ICallback callback;

    public GetUsageHistorySuccessful(GetUsageHistoryRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<Attempt> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            callback.succeed(response.body());
        }
    }
}

class GetUsageHistoryIncomplete implements ICallbackIncomplete<Attempt> {
    private final Context context;
    private final GetUsageHistoryRequest.ICallback callback;

    public GetUsageHistoryIncomplete(Context context, GetUsageHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<Attempt> response) {
        callback.changeUI(false);
        //TODO
        warning(context, "dismissed").show();
    }
}

class GetUsageHistoryFailed implements ICallbackFailure {
    private final Context context;
    private final GetUsageHistoryRequest.ICallback callback;

    public GetUsageHistoryFailed(Context context, GetUsageHistoryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        //TODO
        error(context, "failed").show();
        Log.e("error",t.toString());
    }
}