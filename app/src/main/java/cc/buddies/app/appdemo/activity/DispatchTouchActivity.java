package cc.buddies.app.appdemo.activity;

import android.os.Bundle;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;

public class DispatchTouchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch_view);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        LogUtils.e("DispatchTouchActivity----dispatchTouchEvent");
//        return super.dispatchTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        LogUtils.e("DispatchTouchActivity----onTouchEvent");
//        return super.onTouchEvent(event);
//    }
}
