package cc.buddies.app.appdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private Handler mHandler;

    // 静态内部类，不能持有外部类对象引用。
    // 静态内部类+弱引用。首先介绍下静态内部类。静态内部类，其实可以脱离外部类了，他们是没有联系的。
    // 可以称之为，静态嵌套类。只是为了封装或者什么原因，放在你这里罢了，他是一个独立的类。所以就不会隐式持有外部类的引用。
    // 但是你又必须得到这个引用，怎么办。采用弱引用即可，写一个Handler的静态方类，然后构造方法里 接受activity的实例，把它维护成弱引用即可。
    protected static class MyHandler extends Handler {

        private final WeakReference<BaseActivity> mActivity;

        public MyHandler(BaseActivity activity) {
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }

            super.handleMessage(msg);
            BaseActivity baseActivity = mActivity.get();
            baseActivity.handleMessage(msg);
        }
    }

    public void handleMessage(Message msg) {
        // 子类实现，可接收Handler消息处理Message。
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new MyHandler(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }


}
