package com.loupe.project;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppOpenManager implements Application.ActivityLifecycleCallbacks {

    private int mFinalCount = 0;

    //是否是冷启动
    public static boolean tempCl = true;

    long stopTimeStamp = 0L;

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (mFinalCount == 0) {
            if (tempCl) {
                tempCl = false;
//                FirebaseAnalyticsManager.logEvent(FirebaseAnalyticsManager.Cold_Open)
            } else {
                showAd(activity);
//                FirebaseAnalyticsManager.logEvent(FirebaseAnalyticsManager.Hot_Open)
            }
        }
        mFinalCount++;
    }

    private void showAd(Activity activity) {
//        if (stopTimeStamp > 0 && System.currentTimeMillis() - stopTimeStamp > FirebaseConfigManager.getHotOpenGapTime() * 1000) {
//            MaxManager.showAd(activity, MaxManager.SHOW_AD_TYPE_Hot_Open);
//        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        mFinalCount--;
        if (mFinalCount == 0)
            stopTimeStamp = System.currentTimeMillis();
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
