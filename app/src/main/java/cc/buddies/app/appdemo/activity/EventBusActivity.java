package cc.buddies.app.appdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.eventbus.MessageEvent;
import cc.buddies.app.appdemo.eventbus.UserEvent;
import cc.buddies.app.treasury.utils.LogUtils;

public class EventBusActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onClickButton(View view) {
        EventBus.getDefault().post(new MessageEvent("Hello Message!"));
    }

    public void onClickButton1(View view) {
        EventBus.getDefault().post(new UserEvent("zhao", 20));
    }

    public void onClickIntent(View view) {
        startActivity(new Intent(this, ImageActivity.class));
    }


    // 在主线程执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventA(MessageEvent event) {
        LogUtils.e("EventBusActivity----onMessageEventA()");
    }

    // 发布者在主线程则开启新线程执行，发布者是子线程则在发布者线程中执行。
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventB(MessageEvent event) {
        LogUtils.e("EventBusActivity----onMessageEventB()");
    }

    // 开启新线程
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onUserMessage(UserEvent event) {
        LogUtils.e("EventBusActivity----onUserMessage()----name: " + event.getName() + "    age: " + event.getAge());
    }

    // 在发布者线程中执行
//    @Subscribe(threadMode = ThreadMode.POSTING)

}
