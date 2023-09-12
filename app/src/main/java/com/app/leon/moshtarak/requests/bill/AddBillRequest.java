package com.app.leon.moshtarak.requests.bill;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.cards.BillCardViewModel;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddBillRequest {
    private final Context context;
    private final ICallback callback;
    private final BillCardViewModel bill;

    public AddBillRequest(Context context, BillCardViewModel bill, ICallback callback) {
        this.context = context;
        this.callback = callback;
        this.bill = bill;
    }

    public boolean request() {
        callback.changeUI(false);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<BillCardViewModel> call = iAbfaService.addBill(bill);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new AddBillSuccessful(callback),
                new AddBillIncomplete(context, callback), new AddBillFailed(context, callback));
    }

    public interface ICallback {
        void succeed(BillCardViewModel bill);

        void changeUI(boolean done);
    }
}

class AddBillSuccessful implements ICallbackSucceed<BillCardViewModel> {
    private final AddBillRequest.ICallback callback;

    public AddBillSuccessful(AddBillRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<BillCardViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            callback.succeed(response.body());
        }

    }
}

class AddBillIncomplete implements ICallbackIncomplete<BillCardViewModel> {
    private final Context context;
    private final AddBillRequest.ICallback callback;

    public AddBillIncomplete(Context context, AddBillRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<BillCardViewModel> response) {
        callback.changeUI(true);
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

class AddBillFailed implements ICallbackFailure {
    private final Context context;
    private final AddBillRequest.ICallback callback;

    public AddBillFailed(Context context, AddBillRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(true);
        showFailedMessage(t, context);
    }
}