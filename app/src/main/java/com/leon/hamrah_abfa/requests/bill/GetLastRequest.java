package com.leon.hamrah_abfa.requests.bill;

import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.tables.LastBillViewModel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetLastRequest {
    private final Context context;
    private final ICallback callback;
    private final String id;
    private int zoneId;

    public GetLastRequest(Context context, ICallback callback, String id) {
        this.context = context;
        this.callback = callback;
        this.id = id;
    }

    public GetLastRequest(Context context, ICallback callback, String id, int zoneId) {
        this.context = context;
        this.callback = callback;
        this.id = id;
        this.zoneId = zoneId;
    }

    public boolean request() {
        callback.changeUI(true);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<LastBillViewModel> call;
        if (zoneId == 0)
            call = iAbfaService.getLast(id);
        else
            call = iAbfaService.getThis(id, zoneId);
        return HttpClientWrapper.callHttpAsync(context, call, new GetLastBillSuccessful(callback),
                new GetLastBillIncomplete(context, callback), new GetLastBillFailed(context, callback));
    }

    public interface ICallback {
        void succeed(LastBillViewModel bills);

        void changeUI(boolean show);
    }
}

class GetLastBillSuccessful implements ICallbackSucceed<LastBillViewModel> {
    private final GetLastRequest.ICallback callback;

    public GetLastBillSuccessful(GetLastRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<LastBillViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            callback.succeed(response.body());
        }
    }
}

class GetLastBillIncomplete implements ICallbackIncomplete<LastBillViewModel> {
    private final Context context;
    private final GetLastRequest.ICallback callback;

    public GetLastBillIncomplete(Context context, GetLastRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<LastBillViewModel> response) {
        callback.changeUI(false);
        //TODO
        warning(context, "dismissed").show();
    }
}

class GetLastBillFailed implements ICallbackFailure {
    private final Context context;
    private final GetLastRequest.ICallback callback;

    public GetLastBillFailed(Context context, GetLastRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        //TODO
        error(context, "failed").show();
    }
}