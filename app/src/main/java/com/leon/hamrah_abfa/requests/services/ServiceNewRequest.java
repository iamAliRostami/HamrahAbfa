package com.leon.hamrah_abfa.requests.services;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.enums.FragmentTags.REQUEST_DONE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.fragments.dialog.MessageErrorRequestFragment;
import com.leon.hamrah_abfa.fragments.services.ServicesViewModel;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ServiceNewRequest {
    private final Context context;
    private final ICallback callback;
    private final ServicesViewModel service;

    public ServiceNewRequest(Context context, ICallback callback, ServicesViewModel service) {
        this.context = context;
        this.callback = callback;
        this.service = service;
    }

    public boolean request() {
        callback.changeUI(true);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<ServicesViewModel> call = iAbfaService.requestNew(service);
        return callHttpAsync(context, call, new ServiceNewSuccessful(callback),
                new ServiceNewIncomplete(context, callback), new ServiceNewFailed(context, callback));
    }

    public interface ICallback {
        void succeed(ServicesViewModel service);

        void changeUI(boolean show);
    }
}

class ServiceNewSuccessful implements ICallbackSucceed<ServicesViewModel> {
    private final ServiceNewRequest.ICallback callback;

    public ServiceNewSuccessful(ServiceNewRequest.ICallback callback) {
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

class ServiceNewIncomplete implements ICallbackIncomplete<ServicesViewModel> {

    private final Context context;
    private final ServiceNewRequest.ICallback callback;

    public ServiceNewIncomplete(Context context, ServiceNewRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<ServicesViewModel> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 400) {
            showFragmentDialogOnce(context, REQUEST_DONE.getValue(),
                    MessageErrorRequestFragment.newInstance(error.message(), context.getString(R.string.close),
                            DialogFragment::dismiss));
        } else if (error.status() == 401) {
            expiredToken(context);
        } else {
            warning(context, "dismissed").show();
        }
    }
}

class ServiceNewFailed implements ICallbackFailure {
    private final Context context;
    private final ServiceNewRequest.ICallback callback;

    public ServiceNewFailed(Context context, ServiceNewRequest.ICallback callback) {
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
