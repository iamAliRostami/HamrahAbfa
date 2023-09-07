package com.app.leon.moshtarak.infrastructure;

import retrofit2.Response;

public interface ICallbackIncomplete<T> {
    void executeDismissed(Response<T> response);
}
