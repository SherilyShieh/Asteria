package com.sherily.shieh.asteria.di.component;

import android.app.Activity;

import com.sherily.shieh.asteria.di.module.ActivityModule;
import com.sherily.shieh.asteria.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Administrator on 2016/6/2.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,
            modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
