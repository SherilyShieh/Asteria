package com.sherily.shieh.asteria.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.sherily.shieh.asteria.AsteriaApplication;
import com.sherily.shieh.asteria.di.component.ApplicationComponent;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by xiejiali on 2016/6/1.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initializeTnjector();

    }

    private void initializeTnjector() {
            getApplicationComponent().inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return ((AsteriaApplication) getApplication()).getApplicationComponent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
