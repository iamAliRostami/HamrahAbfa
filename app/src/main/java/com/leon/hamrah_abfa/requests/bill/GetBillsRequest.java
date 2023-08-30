package com.leon.hamrah_abfa.requests.bill;

import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;

import android.content.Context;

import com.leon.hamrah_abfa.di.view_model.HttpClientWrapper;
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
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<BillsSummary> call = iAbfaService.getBills();
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new GetBillsSuccessful(callback),
                new GetBillsIncomplete(context), new GetBillsFailed());
    }

    public interface ICallback {
        void succeed(BillsSummary bills);
    }
}

class GetBillsSuccessful implements ICallbackSucceed<BillsSummary> {
    private final GetBillsRequest.ICallback callback;

    public GetBillsSuccessful(GetBillsRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<BillsSummary> response) {
        if (response.body() != null) {
            if (response.body().billDtos != null)
                callback.succeed(response.body());
        }
    }
}

class GetBillsIncomplete implements ICallbackIncomplete<BillsSummary> {
    private final Context context;

    public GetBillsIncomplete(Context context) {
        this.context = context;
    }

    @Override
    public void executeDismissed(Response<BillsSummary> response) {
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        }
    }
}

class GetBillsFailed implements ICallbackFailure {

    public GetBillsFailed() {
    }

    @Override
    public void executeFailed(Throwable t) {

    }
}