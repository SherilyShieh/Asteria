package com.sherily.shieh.asteria.di.module;

import android.content.Context;

import com.sherily.shieh.asteria.AsteriaApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiejiali on 2016/6/2.
 */
@Module
public class ApplicationModule {

    private final AsteriaApplication asteriaApplication;

    public ApplicationModule(AsteriaApplication asteriaApplication) {
        this.asteriaApplication = asteriaApplication;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return asteriaApplication;
    }

}
