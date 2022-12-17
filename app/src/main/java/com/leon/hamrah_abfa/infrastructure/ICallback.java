package com.leon.hamrah_abfa.infrastructure;

import retrofit2.Response;

public interface ICallback<T> {
    void execute(Response<T> response);
}
