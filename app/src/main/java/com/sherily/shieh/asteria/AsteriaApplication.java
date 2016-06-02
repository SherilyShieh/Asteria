package com.sherily.shieh.asteria;

import android.app.Application;

import com.sherily.shieh.asteria.di.component.ApplicationComponent;
import com.sherily.shieh.asteria.di.component.DaggerApplicationComponent;
import com.sherily.shieh.asteria.di.module.ApplicationModule;

/**
 * Created by xiejiali on 2016/6/1.
 */
public class AsteriaApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
