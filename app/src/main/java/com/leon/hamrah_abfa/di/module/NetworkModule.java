package com.leon.hamrah_abfa.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.leon.hamrah_abfa.di.view_model.APIClientModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

@Module
public class NetworkModule {
    final APIClientModel networkHelper;

    public NetworkModule() {
        networkHelper = new APIClientModel();
    }

    @Singleton
    @Provides
    public APIClientModel providesNetworkHelperModel() {
        return networkHelper;
    }

    @Singleton
    @Provides
    public Gson providesGson() {
        return networkHelper.getGson();
    }

    @Singleton
    @Provides
    public Retrofit providesRetrofit() {
        return networkHelper.getClient();
    }

    @Singleton
    @Provides
    public HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        return networkHelper.getInterceptor();
    }
}
