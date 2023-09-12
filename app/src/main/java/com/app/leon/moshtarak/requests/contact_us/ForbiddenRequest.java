package com.app.leon.moshtarak.requests.contact_us;

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
import com.app.leon.moshtarak.fragments.citizen.ForbiddenViewModel;
import com.app.leon.moshtarak.fragments.dialog.MessageErrorRequestFragment;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForbiddenRequest {
    private final Context context;
    private final ICallback callback;
    private final ForbiddenViewModel forbidden;

    public ForbiddenRequest(Context context, ICallback callback, ForbiddenViewModel forbidden) {
        this.context = context;
        this.callback = callback;
        this.forbidden = forbidden;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<ForbiddenViewModel> call = iAbfaService.forbidden(forbidden.file,
                RequestBody.create(forbidden.getDescription(), MediaType.parse("text/plain")),
                RequestBody.create(String.format("%s - %s", forbidden.getParentType(), forbidden.getType()),
                        MediaType.parse("text/plain")),
                RequestBody.create(forbidden.getPreEshterak(), MediaType.parse("text/plain")),
                RequestBody.create(forbidden.getNextEshterak(), MediaType.parse("text/plain")),
                RequestBody.create(forbidden.getPostalCode(), MediaType.parse("text/plain")),
                RequestBody.create(forbidden.getTedadVahed(), MediaType.parse("text/plain")),
                RequestBody.create(forbidden.getX(), MediaType.parse("text/plain")),
                RequestBody.create(forbidden.getY(), MediaType.parse("text/plain"))

        );
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new ForbiddenSuccessful(callback),
                new ForbiddenIncomplete(context, callback), new ForbiddenFailed(context, callback));
    }

    public interface ICallback {
        void succeed(ForbiddenViewModel forbidden);

        void changeUI(boolean show);
    }
}

class ForbiddenSuccessful implements ICallbackSucceed<ForbiddenViewModel> {
    private final ForbiddenRequest.ICallback callback;

    public ForbiddenSuccessful(ForbiddenRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<ForbiddenViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.succeed(response.body());
        }
    }
}

class ForbiddenIncomplete implements ICallbackIncomplete<ForbiddenViewModel> {

    private final Context context;
    private final ForbiddenRequest.ICallback callback;

    public ForbiddenIncomplete(Context context, ForbiddenRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<ForbiddenViewModel> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else if (error.status() == 400) {
            warning(context,error.message()).show();
        } else if (error.status() == 500) {
            warning(context, R.string.server_error).show();
        }
    }
}

class ForbiddenFailed implements ICallbackFailure {
    private final Context context;
    private final ForbiddenRequest.ICallback callback;

    public ForbiddenFailed(Context context, ForbiddenRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}
