package cc.buddies.app.appdemo.eventbus;

import cc.buddies.app.treasury.utils.LogUtils;

public class MessageEvent {

    public MessageEvent(String message) {
        LogUtils.e("MessageEvent构造: " + message);
    }
}
