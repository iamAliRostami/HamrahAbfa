package com.app.leon.moshtarak.requests.bill;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.cards.BillCardViewModel;
import com.app.leon.moshtarak.fragments.dashboard.PaymentStats;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditBillRequest {
    private final Context context;
    private final ICallback callback;
    private final BillCardViewModel bill;

    public EditBillRequest(Context context, ICallback callback, BillCardViewModel bill) {
        this.context = context;
        this.callback = callback;
        this.bill = bill;
    }

    public boolean request() {
        callback.changeUI(false);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<PaymentStats> call = iAbfaService.editBill(bill);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new EditBillSuccessful(callback),
                new EditBillIncomplete(context, callback), new EditBillFailed(context, callback));
    }

    public interface ICallback {
        void succeed(PaymentStats paymentStats);

        void changeUI(boolean show);
    }
}

class EditBillSuccessful implements ICallbackSucceed<PaymentStats> {
    private final EditBillRequest.ICallback callback;

    public EditBillSuccessful(EditBillRequest.ICallback callback) {
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

class EditBillIncomplete implements ICallbackIncomplete<PaymentStats> {

    private final Context context;
    private final EditBillRequest.ICallback callback;

    public EditBillIncomplete(Context context, EditBillRequest.ICallback callback) {
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

class EditBillFailed implements ICallbackFailure {
    private final Context context;
    private final EditBillRequest.ICallback callback;

    public EditBillFailed(Context context, EditBillRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(true);
        showFailedMessage(t, context);
    }
}
