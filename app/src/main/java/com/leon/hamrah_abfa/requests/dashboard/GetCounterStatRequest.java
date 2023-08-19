package com.leon.hamrah_abfa.requests.dashboard;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.hamrah_abfa.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.fragments.dashboard.CounterStats;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetCounterStatRequest {

    private final Context context;
    private final ICallback callback;
    private final String id;

    public GetCounterStatRequest(Context context, ICallback callback, String id) {
        this.context = context;
        this.callback = callback;
        this.id = id;
    }

    public boolean request() {
        callback.changeUI(true);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        //TODO
        Call<CounterStats> call = iAbfaService.getCounterStat(id);
        return callHttpAsync(context, call, new BillCounterStatSuccessful(callback),
                new BillCounterStatIncomplete(context, callback), new BillCounterStatFailed(context, callback));
    }

    public interface ICallback {
        void succeed(CounterStats counterStats);

        void changeUI(boolean show);
    }
}

class BillCounterStatSuccessful implements ICallbackSucceed<CounterStats> {
    private final GetCounterStatRequest.ICallback callback;

    public BillCounterStatSuccessful(GetCounterStatRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<CounterStats> response) {
        if (response.body() != null) {
            callback.succeed(response.body());
        }
        callback.changeUI(false);
    }
}

class BillCounterStatIncomplete implements ICallbackIncomplete<CounterStats> {

    private final Context context;
    private final GetCounterStatRequest.ICallback callback;

    public BillCounterStatIncomplete(Context context, GetCounterStatRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<CounterStats> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            warning(context, "dismissed").show();
        }
    }
}

class BillCounterStatFailed implements ICallbackFailure {
    private final Context context;
    private final GetCounterStatRequest.ICallback callback;

    public BillCounterStatFailed(Context context, GetCounterStatRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}
