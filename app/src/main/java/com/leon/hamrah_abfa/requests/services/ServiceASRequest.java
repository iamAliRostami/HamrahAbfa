package com.leon.hamrah_abfa.requests.services;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.fragments.services.ServicesViewModel;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ServiceASRequest {
    private final Context context;
    private final ICallback callback;
    private final ServicesViewModel service;

    public ServiceASRequest(Context context, ICallback callback, ServicesViewModel service) {
        this.context = context;
        this.callback = callback;
        this.service = service;
    }

    public boolean request() {
        callback.changeUI(true);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<ServicesViewModel> call = iAbfaService.requestAfter(service);
        return callHttpAsync(context, call, new ServiceASSuccessful(callback),
                new ServiceASIncomplete(context, callback), new ServiceASFailed(context, callback));
    }

    public interface ICallback {
        void succeed(ServicesViewModel service);

        void changeUI(boolean show);
    }
}

class ServiceASSuccessful implements ICallbackSucceed<ServicesViewModel> {
    private final ServiceASRequest.ICallback callback;

    public ServiceASSuccessful(ServiceASRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<ServicesViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.succeed(response.body());
        }
    }
}

class ServiceASIncomplete implements ICallbackIncomplete<ServicesViewModel> {

    private final Context context;
    private final ServiceASRequest.ICallback callback;

    public ServiceASIncomplete(Context context, ServiceASRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<ServicesViewModel> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 400) {
            warning(context, error.message()).show();
            return;
        }
        warning(context, "dismissed").show();
    }
}

class ServiceASFailed implements ICallbackFailure {
    private final Context context;
    private final ServiceASRequest.ICallback callback;

    public ServiceASFailed(Context context, ServiceASRequest.ICallback callback) {
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
