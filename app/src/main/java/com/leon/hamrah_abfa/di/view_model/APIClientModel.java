package com.leon.hamrah_abfa.di.view_model;

import static com.leon.hamrah_abfa.helpers.DifferentCompanyManager.getBaseUrl;
import static com.leon.hamrah_abfa.helpers.MyApplication.getInstance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public final class APIClientModel {
    private final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private final boolean RETRY_ENABLED = false;
    private final long READ_TIMEOUT = 120;
    private final long WRITE_TIMEOUT = 60;
    private final long CONNECT_TIMEOUT = 10;
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    Gson gson;
    @Inject
    Retrofit retrofit;
    @Inject
    HttpLoggingInterceptor interceptor;

    @Inject
    public HttpLoggingInterceptor getInterceptor() {
        if (interceptor == null) {
            interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
        }
        return interceptor;
    }

    @Inject
    public OkHttpClient getHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder().readTimeout(READ_TIMEOUT, TIME_UNIT)
                    .writeTimeout(WRITE_TIMEOUT, TIME_UNIT).connectTimeout(CONNECT_TIMEOUT, TIME_UNIT)
                    .retryOnConnectionFailure(RETRY_ENABLED).addInterceptor(getInterceptor()).build();
        }
        return okHttpClient;
    }

    @Inject
    public OkHttpClient getHttpClient(int timeDivider) {
        if (timeDivider == 1 || timeDivider <= 0) return getHttpClient();
        return new OkHttpClient.Builder().readTimeout(READ_TIMEOUT / timeDivider, TIME_UNIT)
                .writeTimeout(WRITE_TIMEOUT / timeDivider, TIME_UNIT)
                .connectTimeout(CONNECT_TIMEOUT, TIME_UNIT).retryOnConnectionFailure(RETRY_ENABLED)
                .addInterceptor(getInterceptor()).build();
    }

    @Inject
    public OkHttpClient getHttpClient(int readTimeout, int writeTimeout, int connectTimeout) {
        if (readTimeout <= 1 || writeTimeout <= 1 || connectTimeout <= 1) return getHttpClient();
        return new OkHttpClient.Builder().readTimeout(readTimeout, TIME_UNIT)
                .writeTimeout(writeTimeout, TIME_UNIT).connectTimeout(connectTimeout, TIME_UNIT)
                .retryOnConnectionFailure(RETRY_ENABLED).addInterceptor(getInterceptor()).build();
    }

    @Inject
    public Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(getBaseUrl())
                    .client(getInstance().getApplicationComponent().NetworkHelperModel().getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(getInstance().getApplicationComponent().Gson()))
                    .addConverterFactory(ScalarsConverterFactory.create()).build();
        }
        return retrofit;
    }

    @Inject
    public Retrofit getClient(int timeDivider) {
        if (timeDivider == 1 || timeDivider <= 0) return getClient();
        return new Retrofit.Builder().baseUrl(getBaseUrl())
                .client(getInstance().getApplicationComponent().NetworkHelperModel().getHttpClient(timeDivider))
                .addConverterFactory(GsonConverterFactory.create(getInstance().getApplicationComponent().Gson()))
                .addConverterFactory(ScalarsConverterFactory.create()).build();
    }


    @Inject
    public Retrofit getClient(int readTimeout, int writeTimeout, int connectTimeout) {
        if (readTimeout == 1 || readTimeout <= 0 || writeTimeout == 1 || writeTimeout <= 0 ||
                connectTimeout == 1 || connectTimeout <= 0) return getClient();
        return new Retrofit.Builder().baseUrl(getBaseUrl())
                .client(new OkHttpClient.Builder().readTimeout(readTimeout, TIME_UNIT)
                        .writeTimeout(writeTimeout, TIME_UNIT).connectTimeout(connectTimeout, TIME_UNIT)
                        .retryOnConnectionFailure(RETRY_ENABLED).addInterceptor(getInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create(getInstance().getApplicationComponent().Gson()))
                .build();
    }

    @Inject
    public Gson getGson() {
        if (gson == null) gson = new GsonBuilder().setLenient().create();
        return gson;
    }


}