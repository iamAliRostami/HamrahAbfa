package com.app.leon.moshtarak.requests.bill;

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
import com.app.leon.moshtarak.fragments.dialog.MessageErrorRequestFragment;
import com.app.leon.moshtarak.fragments.last_bill.BillViewModel;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

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
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<BillViewModel> call;
        if (zoneId == 0)
            call = iAbfaService.getLast(uuid);
        else
            call = iAbfaService.getThis(id, zoneId);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new GetBillSuccessful(callback),
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
        if (response.body() != null) {
            callback.changeUI(true);
            callback.succeed(response.body());
        }
        callback.changeUI(false);
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
        } else if (error.status() == 400) {
            showFragmentDialogOnce(context, REQUEST_DONE.getValue(),
                    MessageErrorRequestFragment.newInstance(error.message(), context.getString(R.string.close),
                            DialogFragment::dismiss));
        } else if (error.status() == 500) {
            warning(context, R.string.server_error).show();
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