package com.leon.hamrah_abfa.requests.change_mobile;

import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;
import android.util.Log;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.fragments.change_mobile.ChangeMobileViewModel;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangeMobileRequest {
    private final Context context;
    private final ICallback callback;
    private final ChangeMobileViewModel changeMobile;

    public ChangeMobileRequest(Context context, ChangeMobileViewModel changeMobile, ICallback callback) {
        this.context = context;
        this.changeMobile = changeMobile;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(false);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<ChangeMobileViewModel> call = iAbfaService.changeMobile(changeMobile);
        return HttpClientWrapper.callHttpAsync(context, call, new ChangeMobileSuccessful(callback),
                new ChangeMobileIncomplete(context, callback), new ChangeMobileFailed(context, callback));
    }

    public interface ICallback {
        void succeed(String message);

        void changeUI(boolean done);
    }
}

class ChangeMobileSuccessful implements ICallbackSucceed<ChangeMobileViewModel> {
    private final ChangeMobileRequest.ICallback callback;

    public ChangeMobileSuccessful(ChangeMobileRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<ChangeMobileViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            callback.succeed(response.body().getMessage());
        }

    }
}

class ChangeMobileIncomplete implements ICallbackIncomplete<ChangeMobileViewModel> {
    private final Context context;
    private final ChangeMobileRequest.ICallback callback;

    public ChangeMobileIncomplete(Context context, ChangeMobileRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<ChangeMobileViewModel> response) {
        callback.changeUI(true);
        APIError error = parseError(response);
        if (error.status() == 400) {
            warning(context, error.message()).show();
            return;
        }
        //TODO
        warning(context, "dismissed").show();
    }
}

class ChangeMobileFailed implements ICallbackFailure {
    private final Context context;
    private final ChangeMobileRequest.ICallback callback;

    public ChangeMobileFailed(Context context, ChangeMobileRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(true);
        Log.e("error", t.toString());
        //TODO
        error(context, "failed").show();
    }
}
