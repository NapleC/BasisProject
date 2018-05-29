package com.dxs.stc.utils.rxbus;

/**
 * created by hl at 2018/5/9
 * com.naple.devmvp.utils
 *  自定义消息类配合 RxBus 使用
 */
public class MsgEvent<T> {
    private T data;

    private String mMsg;
    private int type;
    private int request;

    public MsgEvent(T data) {
        this.data = data;
    }

    public MsgEvent(String mMsg, int type, int request) {
        this.mMsg = mMsg;
        this.type = type;
        this.request = request;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return mMsg;
    }

    public int getType() {
        return type;
    }

    public int getRequest() {
        return request;
    }

    @Override
    public String toString() {
        return "MsgEvent{" +
                "data=" + data +
                ", mMsg='" + mMsg + '\'' +
                ", type=" + type +
                ", request=" + request +
                '}';
    }
}
