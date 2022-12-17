package com.leon.hamrah_abfa.di.module;


import static com.leon.hamrah_abfa.di.view_model.CustomProgressModel.getInstance;

import com.leon.hamrah_abfa.di.view_model.CustomProgressModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CustomProgressModule {
    private final CustomProgressModel progress;

    public CustomProgressModule() {
        progress = getInstance();
    }

    @Singleton
    @Provides
    public CustomProgressModel providesCustomProgressModel() {
        return progress;
    }
}
