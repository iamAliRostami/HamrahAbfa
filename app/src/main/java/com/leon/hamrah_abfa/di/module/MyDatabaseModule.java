package com.leon.hamrah_abfa.di.module;

import static com.leon.hamrah_abfa.di.view_model.MyDatabaseClientModel.getInstance;
import static com.leon.hamrah_abfa.helpers.Constants.DB_NAME;

import android.content.Context;

import androidx.room.Room;

import com.leon.hamrah_abfa.utils.MyDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MyDatabaseModule {
    private final MyDatabase database;

    public MyDatabaseModule(Context context) {
        this.database = getInstance(context).getMyDatabase();

    }
    @Singleton
    @Provides
    public MyDatabase providesMyDatabase() {
        return database;
    }
}
