package cc.buddies.app.appdemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * 下载启动页图片IntentService
 */
public class SplashImageIntentService extends IntentService {

    private static final String ACTION_DOWNLOAD_SPLASH = "cc.buddies.app.appdemo.service.action.DOWNLOAD_SPLASH";

    private static final String EXTRA_DOWNLOAD_SPLASH = "cc.buddies.app.appdemo.service.extra.DOWNLOAD_SPLASH";

    public SplashImageIntentService() {
        super("SplashImageIntentService");
    }

    /**
     * 启动下载启动页图片任务
     * @see IntentService
     */
    public static void startActionDownloadSplash(Context context, String imageUrl) {
        Intent intent = new Intent(context, SplashImageIntentService.class);
        intent.setAction(ACTION_DOWNLOAD_SPLASH);
        intent.putExtra(EXTRA_DOWNLOAD_SPLASH, imageUrl);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (EXTRA_DOWNLOAD_SPLASH.equals(action)) {
                final String param_splash = intent.getStringExtra(EXTRA_DOWNLOAD_SPLASH);
                handleActionDownloadSplash(param_splash);
            }
        }
    }

    /**
     * 开始下载启动页图片任务
     */
    private void handleActionDownloadSplash(String imageUrl) {

    }

}
