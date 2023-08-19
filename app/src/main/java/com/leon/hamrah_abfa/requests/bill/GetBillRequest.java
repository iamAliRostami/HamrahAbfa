package com.leon.hamrah_abfa.requests.bill;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.hamrah_abfa.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.fragments.last_bill.BillViewModel;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetBillRequest {
    private final Context context;
    private final ICallback callback;
    private String uuid;
    private int zoneId;
    private int id;

    public GetBillRequest(Context context, ICallback callback, String uuid) {
        this.context = context;
        this.callback = callback;
        this.uuid = uuid;
    }

    public GetBillRequest(Context context, ICallback callback, int id, int zoneId) {
        this.context = context;
        this.callback = callback;
        this.id = id;
        this.zoneId = zoneId;
    }

    public boolean request() {
        callback.changeUI(true);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<BillViewModel> call;
        if (zoneId == 0)
            call = iAbfaService.getLast(uuid);
        else
            call = iAbfaService.getThis(id, zoneId);
        return callHttpAsync(context, call, new GetBillSuccessful(callback),
                new GetBillIncomplete(context, callback), new GetBillFailed(context, callback));
    }

    public interface ICallback {
        void succeed(BillViewModel bill);

        void changeUI(boolean show);
    }
}

class GetBillSuccessful implements ICallbackSucceed<BillViewModel> {
    private final GetBillRequest.ICallback callback;

    public GetBillSuccessful(GetBillRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<BillViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            callback.succeed(response.body());
        }
    }
}

class GetBillIncomplete implements ICallbackIncomplete<BillViewModel> {
    private final Context context;
    private final GetBillRequest.ICallback callback;

    public GetBillIncomplete(Context context, GetBillRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<BillViewModel> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            //TODO
            warning(context, "dismissed").show();
        }
    }
}

class GetBillFailed implements ICallbackFailure {
    private final Context context;
    private final GetBillRequest.ICallback callback;

    public GetBillFailed(Context context, GetBillRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}