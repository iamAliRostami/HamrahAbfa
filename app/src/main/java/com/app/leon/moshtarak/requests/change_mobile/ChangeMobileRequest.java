package com.app.leon.moshtarak.requests.change_mobile;

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
import com.app.leon.moshtarak.fragments.change_mobile.ChangeMobileViewModel;
import com.app.leon.moshtarak.fragments.dialog.MessageErrorRequestFragment;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangeMobileRequest {
    private final Context context;
    private final ICallback callback;
    private final ChangeMobileViewModel changeMobile;

    public ChangeMobileRequest(Context context, ChangeMobileViewModel changeMobile, ICallback callback) {
        this.context = context;
        this.changeMobile = changeMobile;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(false);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<ChangeMobileViewModel> call = iAbfaService.changeMobile(changeMobile);
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new ChangeMobileSuccessful(callback),
                new ChangeMobileIncomplete(context, callback), new ChangeMobileFailed(context, callback));
    }

    public interface ICallback {
        void succeed(String message);

        void changeUI(boolean done);
    }
}

class ChangeMobileSuccessful implements ICallbackSucceed<ChangeMobileViewModel> {
    private final ChangeMobileRequest.ICallback callback;

    public ChangeMobileSuccessful(ChangeMobileRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<ChangeMobileViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.changeUI(true);
            callback.succeed(response.body().getMessage());
        }

    }
}

class ChangeMobileIncomplete implements ICallbackIncomplete<ChangeMobileViewModel> {
    private final Context context;
    private final ChangeMobileRequest.ICallback callback;

    public ChangeMobileIncomplete(Context context, ChangeMobileRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<ChangeMobileViewModel> response) {
        callback.changeUI(true);
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

class ChangeMobileFailed implements ICallbackFailure {
    private final Context context;
    private final ChangeMobileRequest.ICallback callback;

    public ChangeMobileFailed(Context context, ChangeMobileRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(true);
        showFailedMessage(t, context);
    }
}
