package com.app.leon.moshtarak.requests.counter;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.counter.CounterViewModel;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

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
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<CounterViewModel> call = iAbfaService.generateBill(counter);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new GenerateBillSuccessful(callback),
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
        } else if (error.status() == 401) {
            expiredToken(context);
        } else {
            //TODO
            warning(context, "dismissed").show();
        }
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
        showFailedMessage(t, context);
    }
}
