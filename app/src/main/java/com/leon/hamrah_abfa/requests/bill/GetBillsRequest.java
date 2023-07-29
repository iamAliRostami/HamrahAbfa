package com.leon.hamrah_abfa.requests.bill;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.fragments.cards.BillsSummary;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetBillsRequest {
    private final Context context;
    private final ICallback callback;

    public GetBillsRequest(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(true);
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<BillsSummary> call = iAbfaService.getBills();
        return callHttpAsync(context, call, new GetBillsSuccessful(callback),
                new GetBillsIncomplete(context, callback), new GetBillsFailed(context, callback));
    }

    public interface ICallback {
        void succeed(BillsSummary bills);

        void changeUI(boolean show);
    }
}

class GetBillsSuccessful implements ICallbackSucceed<BillsSummary> {
    private final GetBillsRequest.ICallback callback;

    public GetBillsSuccessful(GetBillsRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<BillsSummary> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            if (response.body().billDtos != null)
                callback.succeed(response.body());
        }
    }
}

class GetBillsIncomplete implements ICallbackIncomplete<BillsSummary> {
    private final Context context;
    private final GetBillsRequest.ICallback callback;

    public GetBillsIncomplete(Context context, GetBillsRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<BillsSummary> response) {
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

class GetBillsFailed implements ICallbackFailure {
    private final Context context;
    private final GetBillsRequest.ICallback callback;

    public GetBillsFailed(Context context, GetBillsRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        //TODO
        error(context, "failed").show();
    }
}