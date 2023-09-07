package com.app.leon.moshtarak.di.module;

import android.content.Context;

import com.app.leon.moshtarak.di.view_model.SharedPreferenceModel;
import com.app.leon.moshtarak.enums.SharedReferenceNames;

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
