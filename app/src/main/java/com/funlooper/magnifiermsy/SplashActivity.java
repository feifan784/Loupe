package com.funlooper.magnifiermsy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.splashad.api.ATSplashAd;
import com.anythink.splashad.api.ATSplashAdExtraInfo;
import com.anythink.splashad.api.ATSplashAdListener;
import com.github.zackratos.ultimatebar.UltimateBar;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity implements ATSplashAdListener {

    private boolean needShowSplashAd;
    private FrameLayout consRoot;

    private ATSplashAd splashAd;

    private boolean inForeBackground = true;
    private boolean needJump = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        consRoot = findViewById(R.id.consRoot);
        initStatusBar();

        if (AppOpenManager.tempCl) {
            loadAd();
        } else {
            jumpToMain2Activity();
        }

    }

    private void jumpToMainActivity() {
        if (!needJump) {
            needJump = true;
            return;
        }
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void jumpToMain2Activity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadAd() {
        String defaultConfig = "";

        //设置首次开屏广告广告源，请从TopOn后台兜底开屏广告源导出配置
        /*defaultConfig = "{\"unit_id\":1442678,\"nw_firm_id\":15,\"adapter_class\":\"com.anythink.network.toutiao.TTATSplashAdapter\",\"content\":\"{\\\"button_type\\\":\\\"0\\\",\\\"dl_type\\\":\\\"0\\\",\\\"slot_id\\\":\\\"100011\\\",\\\"personalized_template\\\":\\\"0\\\",\\\"zoomoutad_sw\\\":\\\"1\\\",\\\"app_id\\\":\\\"5001121\\\"}\"}";*/
        splashAd = new ATSplashAd(this, "b6364fcd1e1f67", this, 5000, defaultConfig);
        if (splashAd.isAdReady()) {
            FirebaseAnalyticsManager.getInstance().init(SplashActivity.this)
                    .logEvent(FirebaseAnalyticsManager.ADSDK_SHOW);
            splashAd.show(this, consRoot);
        } else {
            FirebaseAnalyticsManager.getInstance().init(SplashActivity.this)
                    .logEvent(FirebaseAnalyticsManager.SHOW_INSERT_NOT_READY);
            splashAd.loadAd();
        }
    }

    @Override
    public void onAdLoaded(boolean b) {
        if (inForeBackground) {
            if (splashAd.isAdReady()) {
                FirebaseAnalyticsManager.getInstance().init(SplashActivity.this)
                        .logEvent(FirebaseAnalyticsManager.ADSDK_SHOW);
                splashAd.show(this, consRoot);
            } else {
                FirebaseAnalyticsManager.getInstance().init(SplashActivity.this)
                        .logEvent(FirebaseAnalyticsManager.SHOW_INSERT_NOT_READY);
                jumpToMainActivity();
            }
        } else {
            needShowSplashAd = true;
        }
    }


    @Override
    public void onAdLoadTimeout() {
        jumpToMain2Activity();
    }

    @Override
    public void onNoAdError(AdError adError) {
        jumpToMain2Activity();
    }

    @Override
    public void onAdShow(ATAdInfo atAdInfo) {

    }

    @Override
    public void onAdClick(ATAdInfo atAdInfo) {
        //点击开屏广告

    }

    @Override
    public void onAdDismiss(ATAdInfo atAdInfo, ATSplashAdExtraInfo atSplashAdExtraInfo) {
        jumpToMain2Activity();
    }

    private void initStatusBar() {
        //取消状态栏
        if (CommonLoupeSetUtils.canLightLoupeStatusBar()) {
            //判断是否支持
            UltimateBar.newTransparentBuilder()
                    // 状态栏颜色
                    .statusColor(Color.TRANSPARENT)
                    // 状态栏透明度
                    .statusAlpha(50).build(this).apply();
        }

        //状态内容颜色
        CommonLoupeSetUtils.onlyLoupeLightStatusbarTextDark(this.getWindow(), false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inForeBackground = true;

        if (needShowSplashAd) {
            needShowSplashAd = false;
            if (splashAd.isAdReady()) {
//                FirebaseAnalyticsManager.logEvent(FirebaseAnalyticsManager.Sp_Show)
                splashAd.show(this, consRoot);
            }
        }
        if (needJump) {
            jumpToMainActivity();
        }
        needJump = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        needJump = false;
        inForeBackground = false;
    }
}