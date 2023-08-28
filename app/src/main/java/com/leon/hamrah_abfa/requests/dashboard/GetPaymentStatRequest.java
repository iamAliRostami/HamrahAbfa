package com.leon.hamrah_abfa.requests.dashboard;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.hamrah_abfa.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
import com.leon.hamrah_abfa.fragments.dashboard.PaymentStats;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetPaymentStatRequest {

    private final Context context;
    private final ICallback callback;
    private final String id;

    public GetPaymentStatRequest(Context context, ICallback callback, String id) {
        this.context = context;
        this.callback = callback;
        this.id = id;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<PaymentStats> call = iAbfaService.getPaymentStat(id);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new BillPaymentSuccessful(callback),
                new BillPaymentIncomplete(context, callback), new BillPaymentFailed(context, callback));
    }

    public interface ICallback {
        void succeed(PaymentStats paymentStats);

        void changeUI(boolean show);
    }
}

class BillPaymentSuccessful implements ICallbackSucceed<PaymentStats> {
    private final GetPaymentStatRequest.ICallback callback;

    public BillPaymentSuccessful(GetPaymentStatRequest.ICallback callback) {
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

class BillPaymentIncomplete implements ICallbackIncomplete<PaymentStats> {

    private final Context context;
    private final GetPaymentStatRequest.ICallback callback;

    public BillPaymentIncomplete(Context context, GetPaymentStatRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<PaymentStats> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            warning(context, "dismissed").show();
        }
    }
}

class BillPaymentFailed implements ICallbackFailure {
    private final Context context;
    private final GetPaymentStatRequest.ICallback callback;

    public BillPaymentFailed(Context context, GetPaymentStatRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}
