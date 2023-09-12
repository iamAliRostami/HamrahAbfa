package com.app.leon.moshtarak.requests.dashboard;

import static com.app.leon.moshtarak.enums.FragmentTags.REQUEST_DONE;
import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.app.leon.moshtarak.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import androidx.fragment.app.DialogFragment;

import com.app.leon.moshtarak.R;
import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.dashboard.SummaryStats;
import com.app.leon.moshtarak.fragments.dialog.MessageErrorRequestFragment;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetBillSummaryRequest {

    private final Context context;
    private final ICallback callback;
    private final String id;

    public GetBillSummaryRequest(Context context, ICallback callback, String id) {
        this.context = context;
        this.callback = callback;
        this.id = id;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<SummaryStats> call = iAbfaService.getBillSummary(id);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new BillSummarySuccessful(callback),
                new BillSummaryIncomplete(context, callback), new BillSummaryFailed(context, callback));
    }

    public interface ICallback {
        void succeed(SummaryStats summaryStats);

        void changeUI(boolean show);
    }
}

class BillSummarySuccessful implements ICallbackSucceed<SummaryStats> {
    private final GetBillSummaryRequest.ICallback callback;

    public BillSummarySuccessful(GetBillSummaryRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<SummaryStats> response) {
        if (response.body() != null) {
            callback.succeed(response.body());
        }
        callback.changeUI(false);
    }
}

class BillSummaryIncomplete implements ICallbackIncomplete<SummaryStats> {

    private final Context context;
    private final GetBillSummaryRequest.ICallback callback;

    public BillSummaryIncomplete(Context context, GetBillSummaryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<SummaryStats> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        }else if (error.status() == 400) {
            warning(context,error.message()).show();
        } else if (error.status() == 500) {
            warning(context, R.string.server_error).show();
        }
    }
}

class BillSummaryFailed implements ICallbackFailure {
    private final Context context;
    private final GetBillSummaryRequest.ICallback callback;

    public BillSummaryFailed(Context context, GetBillSummaryRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}
