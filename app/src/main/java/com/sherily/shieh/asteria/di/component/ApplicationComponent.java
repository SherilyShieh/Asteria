package com.sherily.shieh.asteria.di.component;

import com.sherily.shieh.asteria.di.module.ApplicationModule;
import com.sherily.shieh.asteria.ui.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xiejiali on 2016/6/2.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity activity);
}
