package com.leon.hamrah_abfa.requests.contact_us;

import static com.leon.hamrah_abfa.di.view_model.HttpClientWrapper.callHttpAsync;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;
import static com.leon.hamrah_abfa.utils.ErrorUtils.expiredToken;
import static com.leon.hamrah_abfa.utils.ErrorUtils.parseError;
import static com.leon.toast.RTLToast.error;
import static com.leon.toast.RTLToast.warning;

import android.content.Context;

import com.leon.hamrah_abfa.fragments.contact_us.FeedbackType;
import com.leon.hamrah_abfa.infrastructure.IAbfaService;
import com.leon.hamrah_abfa.infrastructure.ICallbackFailure;
import com.leon.hamrah_abfa.infrastructure.ICallbackIncomplete;
import com.leon.hamrah_abfa.infrastructure.ICallbackSucceed;
import com.leon.hamrah_abfa.utils.APIError;

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
        final Retrofit retrofit = getInstance().getApplicationComponent().Retrofit();
        final IAbfaService iAbfaService = retrofit.create(IAbfaService.class);
        final Call<ArrayList<FeedbackType>> call = iAbfaService.getSuggestionsTypes();
        return callHttpAsync(context, call, new SuggestionTypeSuccessful(callback),
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
        //TODO
        error(context, "failed").show();
    }
}