package cc.buddies.app.appdemo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.service.MessengerService;
import cc.buddies.app.treasury.utils.LogUtils;

public class MessengerActivity extends BaseActivity {

    private boolean icServiceConnected;
    private Messenger mServerMessenger;
    private Messenger mClientMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10010:
                    LogUtils.e("MessengerActivity收到服务器端回应消息: " + msg.getData().getString("reply"));
                    break;
            }
        }
    }

    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            icServiceConnected = true;
            mServerMessenger = new Messenger(service);
            LogUtils.e("MessengerActivity----onServiceConnected----ComponentName: " + name.toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 在服务被杀死的情况下才会被执行
            LogUtils.e("MessengerActivity----onServiceDisconnected----ComponentName: " + name.toString());
            mServerMessenger = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        // 绑定服务
        bindServiceConnection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解除绑定
        unbindServiceConnection();
    }

    private void bindServiceConnection() {
        if (!icServiceConnected) {
            Intent intent = new Intent(this, MessengerService.class);
            bindService(intent, serviceConn, Context.BIND_AUTO_CREATE);
        }
    }

    private void unbindServiceConnection() {
        if (icServiceConnected) {
            unbindService(serviceConn);
            icServiceConnected = false;

//            Intent intent = new Intent(this, MessengerService.class);
////            stopService(intent);
        }
    }

    public void onClickButton(View view) {
        // 发送消息到服务器端
        Message message = Message.obtain();
        message.what = 10086;
        Bundle data = new Bundle();
        data.putString("msg", "MessengerActivity Message!");
        message.setData(data);

        // 指定回信人，由客户端定义。
        message.replyTo = mClientMessenger;
        try {
            // 通过信使发送消息
            mServerMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
