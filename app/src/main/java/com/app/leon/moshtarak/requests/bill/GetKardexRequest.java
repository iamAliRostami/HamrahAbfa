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
import com.app.leon.moshtarak.fragments.checkout.Kardex;
import com.app.leon.moshtarak.fragments.dialog.MessageErrorRequestFragment;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetKardexRequest {
    private final Context context;
    private final ICallback callback;
    private final String id;

    public GetKardexRequest(Context context, ICallback callback, String id) {
        this.context = context;
        this.callback = callback;
        this.id = id;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<Kardex> call = iAbfaService.getKardex(id);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new GetKardexSuccessful(callback),
                new GetKardexIncomplete(context, callback), new GetKardexFailed(context, callback));
    }

    public interface ICallback {
        void succeed(Kardex kardex);

        void changeUI(boolean show);
    }
}

class GetKardexSuccessful implements ICallbackSucceed<Kardex> {
    private final GetKardexRequest.ICallback callback;

    public GetKardexSuccessful(GetKardexRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<Kardex> response) {
        if (response.body() != null) {
            callback.succeed(response.body());
        }
        callback.changeUI(false);
    }
}

class GetKardexIncomplete implements ICallbackIncomplete<Kardex> {
    private final Context context;
    private final GetKardexRequest.ICallback callback;

    public GetKardexIncomplete(Context context, GetKardexRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<Kardex> response) {
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

class GetKardexFailed implements ICallbackFailure {
    private final Context context;
    private final GetKardexRequest.ICallback callback;

    public GetKardexFailed(Context context, GetKardexRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}