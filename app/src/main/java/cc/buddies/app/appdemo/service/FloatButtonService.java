package cc.buddies.app.appdemo.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import cc.buddies.app.appdemo.R;
import cc.buddies.app.treasury.permission.CheckPermissionUtils;

public class FloatButtonService extends Service {

    /** 是否已经开启悬浮窗 */
    public static boolean isStarted = false;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;


    @Override
    public IBinder onBind(Intent intent) {
        // 悬浮窗不绑定页面，生命周期跟随应用周期。
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isStarted = true;
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 8.0新特性
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mLayoutParams.format = PixelFormat.RGBA_8888;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.width = 500;
        mLayoutParams.height = 100;
        mLayoutParams.x = 300;
        mLayoutParams.y = 300;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startFloatButtonWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startFloatButtonWindow() {
        if (!CheckPermissionUtils.checkManageOverlayPermission(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "无开启悬浮窗权限！", Toast.LENGTH_LONG).show();
            return;
        }

        Button button = new Button(getApplicationContext());
        button.setText("这是一个悬浮按钮");
        button.setBackgroundResource(R.color.colorAccent);
        mWindowManager.addView(button, mLayoutParams);
        button.setOnTouchListener(new FloatOnTouchListener());
    }

    private class FloatOnTouchListener implements View.OnTouchListener {

        private int x;
        private int y;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int moveX = nowX - x;
                    int moveY = nowY - y;
                    x = nowX;
                    y = nowY;
                    mLayoutParams.x += moveX;
                    mLayoutParams.y += moveY;
                    mWindowManager.updateViewLayout(v, mLayoutParams);
                    break;
            }
            return false;
        }
    }


}
