package com.sherily.shieh.asteria;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

import com.sherily.shieh.asteria.baidumap.LocationHelper;
import com.sherily.shieh.asteria.di.component.ApplicationComponent;
import com.sherily.shieh.asteria.di.component.DaggerApplicationComponent;
import com.sherily.shieh.asteria.di.module.ApplicationModule;

/**
 * Created by xiejiali on 2016/6/1.
 */
public class AsteriaApplication extends Application {

    private ApplicationComponent applicationComponent;

    //定位
    //public LocationHelper mLocationHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());

        //百度定位功能初始化
//        mLocationHelper = LocationHelper.sharedInstance(getApplicationContext());
//        mLocationHelper.loadOptions();
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
