package com.leon.hamrah_abfa.requests.counter;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;
import android.util.Log;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.fragments.counter.CounterViewModel;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GenerateBillRequest {
    private final Context context;
    private final ICallback callback;
    private final CounterViewModel counter;

    public GenerateBillRequest(Context context, CounterViewModel counter, ICallback callback) {
        this.context = context;
        this.counter = counter;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(false);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<CounterViewModel> call = iAbfaService.generateBill(counter);
        return callHttpAsync(context, call, new GenerateBillSuccessful(callback),
                new GenerateBillIncomplete(context, callback), new GenerateBillFailed(context, callback));
    }

    public interface ICallback {
        void succeed(String message);

        void changeUI(boolean done);
    }
}

class GenerateBillSuccessful implements ICallbackSucceed<CounterViewModel> {
    private final GenerateBillRequest.ICallback callback;

    public GenerateBillSuccessful(GenerateBillRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<CounterViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            callback.succeed(response.body().getMessage());
        }

    }
}

class GenerateBillIncomplete implements ICallbackIncomplete<CounterViewModel> {
    private final Context context;
    private final GenerateBillRequest.ICallback callback;

    public GenerateBillIncomplete(Context context, GenerateBillRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<CounterViewModel> response) {
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

class GenerateBillFailed implements ICallbackFailure {
    private final Context context;
    private final GenerateBillRequest.ICallback callback;

    public GenerateBillFailed(Context context, GenerateBillRequest.ICallback callback) {
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
