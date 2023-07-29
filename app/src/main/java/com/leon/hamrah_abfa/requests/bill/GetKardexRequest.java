package com.leon.hamrah_abfa.requests.bill;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.fragments.checkout.Kardex;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetKardexRequest {
    private final Context context;
    private final ICallback callback;
    private final String id;

    public GetKardexRequest(Context context, ICallback callback, String id) {
        this.context = context;
        this.callback = callback;
        this.id = id;
    }

    public boolean request() {
        callback.changeUI(true);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<Kardex> call = iAbfaService.getKardex(id);
        return callHttpAsync(context, call, new GetKardexSuccessful(callback),
                new GetKardexIncomplete(context, callback), new GetKardexFailed(context, callback));
    }

    public interface ICallback {
        void succeed(Kardex kardex);

        void changeUI(boolean show);
    }
}

class GetKardexSuccessful implements ICallbackSucceed<Kardex> {
    private final GetKardexRequest.ICallback callback;

    public GetKardexSuccessful(GetKardexRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<Kardex> response) {
        if (response.body() != null) {
            callback.succeed(response.body());
        }
        callback.changeUI(false);
    }
}

class GetKardexIncomplete implements ICallbackIncomplete<Kardex> {
    private final Context context;
    private final GetKardexRequest.ICallback callback;

    public GetKardexIncomplete(Context context, GetKardexRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<Kardex> response) {
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

class GetKardexFailed implements ICallbackFailure {
    private final Context context;
    private final GetKardexRequest.ICallback callback;

    public GetKardexFailed(Context context, GetKardexRequest.ICallback callback) {
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