package com.app.leon.moshtarak.requests.bill;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.dashboard.PaymentStats;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RemoveBillRequest {
    private final Context context;
    private final ICallback callback;
    private final String billId;

    public RemoveBillRequest(Context context, ICallback callback, String billId) {
        this.context = context;
        this.callback = callback;
        this.billId = billId;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<PaymentStats> call = iAbfaService.removeBill(billId);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new RemoveBillSuccessful(callback),
                new RemoveBillIncomplete(context, callback), new RemoveBillFailed(context, callback));
    }

    public interface ICallback {
        void succeed(PaymentStats paymentStats);

        void changeUI(boolean show);
    }
}

class RemoveBillSuccessful implements ICallbackSucceed<PaymentStats> {
    private final RemoveBillRequest.ICallback callback;

    public RemoveBillSuccessful(RemoveBillRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<PaymentStats> response) {
        if (response.body() != null) {
            callback.succeed(response.body());
        }
        callback.changeUI(false);
    }
}

class RemoveBillIncomplete implements ICallbackIncomplete<PaymentStats> {

    private final Context context;
    private final RemoveBillRequest.ICallback callback;

    public RemoveBillIncomplete(Context context, RemoveBillRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<PaymentStats> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 400) {
            warning(context, error.message()).show();
        } else if (error.status() == 401) {
            expiredToken(context);
        } else if (error.status() == 500) {
            warning(context, R.string.server_error).show();
        }
    }
}

class RemoveBillFailed implements ICallbackFailure {
    private final Context context;
    private final RemoveBillRequest.ICallback callback;

    public RemoveBillFailed(Context context, RemoveBillRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}
