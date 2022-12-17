package com.leon.hamrah_abfa.di.module;

import android.content.Context;

import com.leon.hamrah_abfa.di.view_model.SharedPreferenceModel;
import com.leon.hamrah_abfa.enums.SharedReferenceNames;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferenceModule {
    private final SharedPreferenceModel sharedPreference;

    public SharedPreferenceModule(Context context, SharedReferenceNames name) {
        sharedPreference = new SharedPreferenceModel(context, name.getValue());
    }

    @Singleton
    @Provides
    public SharedPreferenceModel providesSharedPreferenceModel() {
        return sharedPreference;
    }
}
