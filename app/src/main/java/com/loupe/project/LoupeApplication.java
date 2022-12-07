package com.loupe.project;

import android.app.Application;

public class LoupeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new AppOpenManager());
    }
}
