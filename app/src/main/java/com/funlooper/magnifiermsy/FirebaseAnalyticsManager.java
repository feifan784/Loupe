package com.funlooper.magnifiermsy;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseAnalyticsManager {

    //进入相册，展示插屏视频
    public final static String PICTURE = "IV_Show_Album";
    //进入摄像，展示插屏视频
    public final static String VIDEO = "IV_Show_Camera";
    //展示插屏视频（全部统计，不区分场景）
    public final static String SHOW_INSERT_ALL = "IV_Show_ALL";
    //各场景下调用到show插屏方法，但是广告未准备好
    public final static String SHOW_INSERT_NOT_READY = "Invoke_Show_Not_Ready";
    //真正调用到广告SDK show方法
    public final static String ADSDK_SHOW = "Invoke_ADSDK_Show";

    private Bundle bundle = new Bundle();

    private FirebaseAnalyticsManager() {
    }

    private static class Holder {
        static FirebaseAnalyticsManager instance = new FirebaseAnalyticsManager();
    }

    public static FirebaseAnalyticsManager getInstance() {
        return Holder.instance;
    }

    public static FirebaseAnalytics firebaseAnalytics;

    public FirebaseAnalyticsManager init(Context context) {
        if (firebaseAnalytics == null) {
            firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        }
        return this;
    }

    public void logEvent(String event) {
        firebaseAnalytics.logEvent(event, bundle);
    }


}
