package com.app.leon.moshtarak.requests.contact_us;

import static com.app.leon.moshtarak.helpers.MyApplication.getInstance;
import static com.app.leon.moshtarak.utils.ErrorUtils.expiredToken;
import static com.app.leon.moshtarak.utils.ErrorUtils.parseError;
import static com.app.leon.moshtarak.utils.ErrorUtils.showFailedMessage;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.app.leon.moshtarak.activities.ContactUsActivity;
import com.app.leon.moshtarak.di.view_model.HttpClientWrapper;
import com.app.leon.moshtarak.fragments.contact_us.FeedbackType;
import com.app.leon.moshtarak.infrastructure.IAbfaService;
import com.app.leon.moshtarak.infrastructure.ICallbackFailure;
import com.app.leon.moshtarak.infrastructure.ICallbackIncomplete;
import com.app.leon.moshtarak.infrastructure.ICallbackSucceed;
import com.app.leon.moshtarak.utils.APIError;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetSuggestionsTypes {
    private final Context context;
    private final ICallback callback;

    public GetSuggestionsTypes(Context context, ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public boolean request() {
        callback.changeUI(true);
        Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        Call<ArrayList<FeedbackType>> call = iAbfaService.getSuggestionsTypes();
        return HttpClientWrapper.callHttpAsyncCancelable(context, call, new SuggestionTypeSuccessful(callback),
                new SuggestionTypeIncomplete(context, callback), new SuggestionTypeFailed(context, callback));


    }

    public interface ICallback {
        void succeed(ArrayList<FeedbackType> feedbackType);

        void changeUI(boolean done);
    }
}

class SuggestionTypeSuccessful implements ICallbackSucceed<ArrayList<FeedbackType>> {
    private final GetSuggestionsTypes.ICallback callback;

    public SuggestionTypeSuccessful(GetSuggestionsTypes.ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void executeCompleted(Response<ArrayList<FeedbackType>> response) {
        callback.succeed(response.body());
        callback.changeUI(false);
    }
}

class SuggestionTypeIncomplete implements ICallbackIncomplete<ArrayList<FeedbackType>> {
    private final GetSuggestionsTypes.ICallback callback;
    private final Context context;

    public SuggestionTypeIncomplete(Context context, GetSuggestionsTypes.ICallback callback) {
        this.callback = callback;
        this.context = context;
    }

    @Override
    public void executeDismissed(Response<ArrayList<FeedbackType>> response) {
        callback.changeUI(false);
        APIError error = parseError(response);
        if (error.status() == 401) {
            expiredToken(context);
        } else {
            warning(context, "dismissed").show();
        }
        ((ContactUsActivity) context).getSupportFragmentManager().popBackStack();
    }
}

class SuggestionTypeFailed implements ICallbackFailure {
    private final Context context;
    private final GetSuggestionsTypes.ICallback callback;

    public SuggestionTypeFailed(Context context, GetSuggestionsTypes.ICallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void executeFailed(Throwable t) {
        callback.changeUI(false);
        showFailedMessage(t, context);
        ((ContactUsActivity) context).getSupportFragmentManager().popBackStack();
    }
}