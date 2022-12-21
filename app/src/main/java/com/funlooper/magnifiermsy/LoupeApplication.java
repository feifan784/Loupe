package com.funlooper.magnifiermsy;

import android.app.Application;

public class LoupeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new AppOpenManager());

        new TopOnManager().init(this);

//        new FirebaseConfigManager().setConfig().fetch(this);
    }
}
