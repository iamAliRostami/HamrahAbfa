package com.app.leon.moshtarak.infrastructure;

import retrofit2.Response;

public interface ICallbackSucceed<T> {
    void executeCompleted(Response<T> response);
}
