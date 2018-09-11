package cc.buddies.app.appdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import cc.buddies.app.treasury.utils.LogUtils;

public class MessengerService extends Service {

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10086:
                    LogUtils.e("MessengerService----MessengerHandler----handleMessage----msg: " + msg.getData().getString("msg"));

                    // 回复消息
                    Messenger replyMessenger = msg.replyTo;
                    Message replyMessage = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "服务器端已经收到消息");
                    replyMessage.what = 10010;
                    replyMessage.setData(bundle);

                    try {
                        replyMessenger.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private Messenger messenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("MessengerService onDestroy!");
    }
}
