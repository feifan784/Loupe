package com.loupe.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.splashad.api.ATSplashAd;
import com.anythink.splashad.api.ATSplashAdExtraInfo;
import com.anythink.splashad.api.ATSplashAdListener;
import com.github.zackratos.ultimatebar.UltimateBar;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity implements ATSplashAdListener {

    private boolean needShowSplashAd;
    private ConstraintLayout consRoot;

    private ATSplashAd splashAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        consRoot = findViewById(R.id.consRoot);
        initStatusBar();

        loadAd();

    }

    private void jumpToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loadAd() {
        String defaultConfig = "";

        //设置首次开屏广告广告源，请从TopOn后台兜底开屏广告源导出配置
        /*defaultConfig = "{\"unit_id\":1442678,\"nw_firm_id\":15,\"adapter_class\":\"com.anythink.network.toutiao.TTATSplashAdapter\",\"content\":\"{\\\"button_type\\\":\\\"0\\\",\\\"dl_type\\\":\\\"0\\\",\\\"slot_id\\\":\\\"100011\\\",\\\"personalized_template\\\":\\\"0\\\",\\\"zoomoutad_sw\\\":\\\"1\\\",\\\"app_id\\\":\\\"5001121\\\"}\"}";*/
        splashAd = new ATSplashAd(this, "b631877cd74a52", this, 5000, defaultConfig);
        if (splashAd.isAdReady()) {
//            FirebaseAnalyticsManager.logEvent(FirebaseAnalyticsManager.Sp_Show);
            splashAd.show(this, consRoot);
        } else {
            splashAd.loadAd();
        }
    }

    @Override
    public void onAdLoaded(boolean b) {
        if (AppOpenManager.tempCl) {
            loadAd();
        } else {
            jumpToMainActivity();
        }
    }

    @Override
    public void onAdLoadTimeout() {
        jumpToMainActivity();
    }

    @Override
    public void onNoAdError(AdError adError) {
        jumpToMainActivity();
    }

    @Override
    public void onAdShow(ATAdInfo atAdInfo) {

    }

    @Override
    public void onAdClick(ATAdInfo atAdInfo) {

    }

    @Override
    public void onAdDismiss(ATAdInfo atAdInfo, ATSplashAdExtraInfo atSplashAdExtraInfo) {

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

    }
}