package cc.buddies.app.appdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import cc.buddies.app.treasury.utils.LogUtils;

public class MyService extends Service {

    public static final String REPLY_BROADCAST_ACTION = "cc.buddies.app.appdemo.service.MyService.REPLY";
    private final IBinder mBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    public void printHello() {
        // 如果执行耗时内容，放到线程中执行。
        LogUtils.e("MyService----printHello()");

        replyHello();
    }

    private void replyHello() {
        // 通过广播回复Activity
        Intent intent = new Intent(REPLY_BROADCAST_ACTION);
        intent.putExtra("reply", "MyService ReplyHello!");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

}
