package com.leon.hamrah_abfa.requests;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.enums.FragmentTags.REQUEST_DONE;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.hamrah_abfa.utils.ShowFragment.showFragmentDialogOnce;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import androidx.fragment.app.DialogFragment;

import com.leon.hamrah_abfa.R;
import com.leon.hamrah_abfa.fragments.contact_us.ForbiddenViewModel;
import com.leon.hamrah_abfa.fragments.dialog.MessageErrorRequestFragment;
import com.leon.hamrah_abfa.fragments.services.ServicesViewModel;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

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
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<ForbiddenViewModel> call = iAbfaService.forbidden(forbidden.file,
                RequestBody.create(forbidden.getDescription(), MediaType.parse("text/plain")),
                RequestBody.create(forbidden.getPreEshterak(), MediaType.parse("text/plain")),
                RequestBody.create(forbidden.getNextEshterak(), MediaType.parse("text/plain")),
                RequestBody.create(forbidden.getPostalCode(), MediaType.parse("text/plain")),
                RequestBody.create(forbidden.getTedadVahed(), MediaType.parse("text/plain")),
                RequestBody.create(forbidden.getX(), MediaType.parse("text/plain")),
                RequestBody.create(forbidden.getY(), MediaType.parse("text/plain"))

        );
        return callHttpAsync(context, call, new ForbiddenSuccessful(callback),
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
        if (error.status() == 400) {
            showFragmentDialogOnce(context, REQUEST_DONE.getValue(),
                    MessageErrorRequestFragment.newInstance(error.message(), context.getString(R.string.close),
                            DialogFragment::dismiss));
            return;
        } else if (error.status() == 401) {
            expiredToken(context);
            return;
        }
        warning(context, "dismissed").show();
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
        //TODO
        error(context, "failed").show();
    }
}
