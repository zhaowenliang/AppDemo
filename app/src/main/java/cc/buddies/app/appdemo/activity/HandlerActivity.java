package cc.buddies.app.appdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;

public class HandlerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        init();
    }

    private void init() {
        // 子线程创建Handler，需要使用Looper.prepare();
        // 子线程中获取主线程Looper，Looper.getMainLooper();
        // 使用匿名内部类，会持有外部类对象，对于Activity来说容易造成内存泄漏。
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Looper.prepare();
//                Handler handler = new Handler();
//                Looper.loop();

//                Handler handler =  new Handler(Looper.getMainLooper());
            }
        });

    }

    // 非静态、匿名内部类会隐式持有外部类的引用。
    // 匿名内部类持有的引用：外部类引用内部类，再引用内部类的方法(此处handlerMessage(Message msg))，
    // 然后此方法会交由msg.callback持有引用，所以message持有handler，handler又持有activity引用。
    // message没有处理完毕的话，activity就释放不了，所以会造成内存泄漏。

//    Handler mmHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    };


    // 此时Handler没有重写内部方法，是个独立的类，所以不会隐式的持有外部类对象的引用。
    // Callback是匿名内部类，持有外部类对象的引用，但是不会交由Message持有。

    Handler mmmHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

}
