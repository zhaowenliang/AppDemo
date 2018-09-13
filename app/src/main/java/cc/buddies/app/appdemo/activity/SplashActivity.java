package cc.buddies.app.appdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.service.SplashImageIntentService;
import cc.buddies.app.treasury.sharedpreferences.SPConfig;
import cc.buddies.app.treasury.sharedpreferences.SPUtils;

public class SplashActivity extends BaseActivity {

    private static final long SPLASH_TOTAL_TIME = 2000;
    private static long CUR_SPLASH_TIME = SPLASH_TOTAL_TIME;
    private static final int NEXT_REQUEST_CODE = 0x100;
    private static boolean isFirstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus && !isFirstStart) {
            return;     // 第一次启动页面、窗口绑定的时候再处理
        }
        isFirstStart = false;
        long attachTime = SPUtils.getLong(this, SPConfig.APP_ATTACH_TIME, 0L);
        long diffTime = System.currentTimeMillis() - attachTime;
        // 闪屏页时间
        CUR_SPLASH_TIME = diffTime < SPLASH_TOTAL_TIME ? (SPLASH_TOTAL_TIME - diffTime) : 0;

        initMain();
        initSplashImage();
    }

    private void initMain() {
        mHandler.sendEmptyMessageDelayed(NEXT_REQUEST_CODE, CUR_SPLASH_TIME);
    }

    /**
     * 启动IntentService后台下载最新启动页图片。
     */
    private void initSplashImage() {
        String splashUrl = "http://s1.knowsky.com/20170227/encmhilxq5f13.png";
        SplashImageIntentService.startActionDownloadSplash(this, splashUrl);
    }

    @Override
    protected void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what == NEXT_REQUEST_CODE) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }
}
