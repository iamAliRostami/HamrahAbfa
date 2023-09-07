package com.app.leon.moshtarak.requests.contact_us;

import static com.app.leon.moshtarak.di.view_model.HttpClientWrapper.callHttpAsyncCached;
import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.fragments.contact_us.FeedbackViewModel;
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

public class RegisterFeedbackRequest {
    private final Context context;
    private final ICallback callback;
    private final FeedbackViewModel viewModel;

    public RegisterFeedbackRequest(Context context, ICallback callback, FeedbackViewModel viewModel) {
        this.context = context;
        this.callback = callback;
        this.viewModel = viewModel;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().NetworkHelperModel().getClientCached(context);
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<FeedbackViewModel> call = iAbfaService.registerFeedback(viewModel.file,
                RequestBody.create(viewModel.getDescription(), MediaType.parse("text/plain")),
                RequestBody.create(viewModel.getSolution(), MediaType.parse("text/plain")),
                RequestBody.create(String.valueOf(viewModel.getFeedbackTypeId()), MediaType.parse("text/plain")),
                RequestBody.create(String.valueOf(viewModel.isInComplaint()), MediaType.parse("text/plain")));
        return callHttpAsyncCached(context, call, new RegisterFeedbackSuccessful(callback),
                new RegisterFeedbackIncomplete(context, callback), new RegisterFeedbackFailed(context, callback));
    }

    public interface ICallback {
        void succeed(FeedbackViewModel viewModel);

        void changeUI(boolean show);
    }
}

class RegisterFeedbackSuccessful implements ICallbackSucceed<FeedbackViewModel> {
    private final RegisterFeedbackRequest.ICallback callback;

    public RegisterFeedbackSuccessful(RegisterFeedbackRequest.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<FeedbackViewModel> response) {
        callback.changeUI(false);
        if (response.body() != null) {
            callback.succeed(response.body());
        }
    }
}

class RegisterFeedbackIncomplete implements ICallbackIncomplete<FeedbackViewModel> {

    private final Context context;
    private final RegisterFeedbackRequest.ICallback callback;

    public RegisterFeedbackIncomplete(Context context, RegisterFeedbackRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeDismissed(Response<FeedbackViewModel> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            warning(context, "dismissed").show();
        }
    }
}

class RegisterFeedbackFailed implements ICallbackFailure {
    private final Context context;
    private final RegisterFeedbackRequest.ICallback callback;

    public RegisterFeedbackFailed(Context context, RegisterFeedbackRequest.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
    }
}
