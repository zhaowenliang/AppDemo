package cc.buddies.app.appdemo;

import android.app.Application;
import android.content.Context;

import cc.buddies.app.treasury.sharedpreferences.SPConfig;
import cc.buddies.app.treasury.sharedpreferences.SPUtils;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SPUtils.setLong(base, SPConfig.APP_ATTACH_TIME, System.currentTimeMillis());
    }

    public void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
