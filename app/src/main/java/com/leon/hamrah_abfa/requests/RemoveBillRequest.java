package com.leon.hamrah_abfa.requests;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.hamrah_abfa.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.fragments.cards.BillCardViewModel;
import com.leon.hamrah_abfa.fragments.dashboard.PaymentStats;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RemoveBillRequest {
    private final Context context;
    private final ICallback callback;
    private final BillCardViewModel bill;

    public RemoveBillRequest(Context context, ICallback callback, BillCardViewModel bill) {
        this.context = context;
        this.callback = callback;
        this.bill = bill;
    }

    public boolean request() {
        callback.changeUI(false);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<PaymentStats> call = iAbfaService.removeBill(bill.getId());
        return callHttpAsync(context, call, new RemoveBillSuccessful(callback),
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
        callback.changeUI(true);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            warning(context, "dismissed").show();
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
        callback.changeUI(true);
        showFailedMessage(t, context);
    }
}
