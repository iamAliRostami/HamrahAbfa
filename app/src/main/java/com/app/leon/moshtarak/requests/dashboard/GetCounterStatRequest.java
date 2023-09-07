package com.app.leon.moshtarak.requests.dashboard;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.dashboard.CounterStats;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

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
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<CounterStats> call = iAbfaService.getCounterStat(id);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new BillCounterStatSuccessful(callback),
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
