package com.leon.hamrah_abfa.infrastructure;

import retrofit2.Response;

public interface ICallbackSucceed<T> {
    void executeCompleted(Response<T> response);
}
