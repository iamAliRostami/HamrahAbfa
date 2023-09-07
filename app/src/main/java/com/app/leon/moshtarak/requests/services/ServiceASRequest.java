package com.app.leon.moshtarak.requests.services;

import static com.app.leon.moshtarak.enums.FragmentTags.REQUEST_DONE;
import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.app.leon.moshtarak.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import androidx.fragment.app.DialogFragment;

import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.dialog.MessageErrorRequestFragment;
import com.app.leon.moshtarak.fragments.services.ServicesViewModel;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;
import com.app.leon.moshtarak.R;

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
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<ServicesViewModel> call = iAbfaService.requestAfter(service);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new ServiceASSuccessful(callback),
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
        showFailedMessage(t, context);
    }
}
