package com.leon.hamrah_abfa.di.view_model;

import static com.leon.hamrah_abfa.helpers.DifferentCompanyManager.getBaseUrl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leon.hamrah_abfa.helpers.MyApplication;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public final class NetworkHelperModel {
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final boolean RETRY_ENABLED = false;
    private static final long READ_TIMEOUT = 120;
    private static final long WRITE_TIMEOUT = 60;
    private static final long CONNECT_TIMEOUT = 10;
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    Gson gson;
    @Inject
    Retrofit retrofit;

    @Inject
    public OkHttpClient getHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
            return new OkHttpClient.Builder().readTimeout(READ_TIMEOUT, TIME_UNIT)
                    .writeTimeout(WRITE_TIMEOUT, TIME_UNIT).connectTimeout(CONNECT_TIMEOUT, TIME_UNIT)
                    .retryOnConnectionFailure(RETRY_ENABLED).addInterceptor(interceptor).build();
        }
        return okHttpClient;
    }

    @Inject
    public OkHttpClient getHttpClient(final int denominator) {
        if (denominator == 1 || denominator <= 0) {
            return getHttpClient();
        }
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().readTimeout(READ_TIMEOUT / denominator, TIME_UNIT)
                .writeTimeout(WRITE_TIMEOUT / denominator, TIME_UNIT)
                .connectTimeout(CONNECT_TIMEOUT, TIME_UNIT).retryOnConnectionFailure(RETRY_ENABLED)
                .addInterceptor(interceptor).build();
    }

    @Inject
    public OkHttpClient getHttpClient(int readTimeout, int writeTimeout, int connectTimeout) {
        if (readTimeout == 1 || readTimeout <= 0 || writeTimeout == 1 || writeTimeout <= 0 ||
                connectTimeout == 1 || connectTimeout <= 0) {
            return getHttpClient();
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().readTimeout(readTimeout, TIME_UNIT)
                .writeTimeout(writeTimeout, TIME_UNIT).connectTimeout(connectTimeout, TIME_UNIT)
                .retryOnConnectionFailure(RETRY_ENABLED).addInterceptor(interceptor).build();
    }

    @Inject
    public Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(getBaseUrl())
                    .client(MyApplication.getInstance().getApplicationComponent().NetworkHelperModel().getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(MyApplication.getInstance().getApplicationComponent().Gson()))
                    .addConverterFactory(ScalarsConverterFactory.create()).build();
        }
        return retrofit;
    }

    @Inject
    public Retrofit getInstance(int denominator) {
        if (denominator == 1 || denominator <= 0) return getInstance();
        return new Retrofit.Builder().baseUrl(getBaseUrl())
                .client(MyApplication.getInstance().getApplicationComponent().NetworkHelperModel().getHttpClient(denominator))
                .addConverterFactory(GsonConverterFactory.create(MyApplication.getInstance().getApplicationComponent().Gson()))
                .addConverterFactory(ScalarsConverterFactory.create()).build();
    }


    @Inject
    public Retrofit getInstance(int readTimeout, int writeTimeout, int connectTimeout) {
        if (readTimeout == 1 || readTimeout <= 0 || writeTimeout == 1 || writeTimeout <= 0 ||
                connectTimeout == 1 || connectTimeout <= 0) {
            return getInstance();
        }
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        return new Retrofit.Builder().baseUrl(getBaseUrl())
                .client(new OkHttpClient.Builder().readTimeout(readTimeout, TIME_UNIT)
                        .writeTimeout(writeTimeout, TIME_UNIT).connectTimeout(connectTimeout, TIME_UNIT)
                        .retryOnConnectionFailure(RETRY_ENABLED).addInterceptor(interceptor).build())
                .addConverterFactory(GsonConverterFactory.create(MyApplication.getInstance().getApplicationComponent().Gson()))
                .build();
    }

    @Inject
    public Gson getGson() {
        if (gson == null) gson = new GsonBuilder().setLenient().create();
        return gson;
    }
}