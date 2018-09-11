package cc.buddies.app.appdemo.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.service.MyService;
import cc.buddies.app.treasury.utils.LogUtils;

public class ServiceActivity extends BaseActivity {

    private boolean icServiceConnected;
    private MyService mService;
    private MyBroadReceiver mBroadReceiver;

    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            icServiceConnected = true;
            mService = ((MyService.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            icServiceConnected = false;
            mService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        // 绑定Service
        bindServiceConnection();
        // 注册Service回复广播接收器
        registerLocalBroadcast();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindServiceConnection();
        unregisterLocalBroadcast();
    }

    private void bindServiceConnection() {
        if (!icServiceConnected) {
            Intent intent = new Intent(this, MyService.class);
            bindService(intent, serviceConn, Context.BIND_AUTO_CREATE);
        }
    }

    private void unbindServiceConnection() {
        if (icServiceConnected) {
            unbindService(serviceConn);
            icServiceConnected = false;
        }
    }

    private void registerLocalBroadcast() {
        mBroadReceiver = new MyBroadReceiver();
        IntentFilter filter = new IntentFilter(MyService.REPLY_BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mBroadReceiver, filter);
    }

    private void unregisterLocalBroadcast() {
        if (mBroadReceiver != null) {
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mBroadReceiver);
            mBroadReceiver = null;
        }
    }

    public void onClickButton(View view) {
        if (mService == null) {
            LogUtils.e("ServiceActivity----mService is null!");
            return;
        }

        mService.printHello();
    }

    private class MyBroadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String reply = intent.getStringExtra("reply");
            LogUtils.e("ServiceActivity----MyBroadReceiver----onReceive: " + reply);
        }
    }

}
