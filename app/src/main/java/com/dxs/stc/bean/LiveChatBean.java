package com.dxs.stc.bean;

/**
 * created by hl at 2018/7/4
 * com.dxs.stc.bean.LiveChatBean
 *
 * @version V1.0 <描述当前版本功能>
 */
public class LiveChatBean {

    public static final int OTHER = 0;
    public static final int MINE = 1;

    private int chatType;
    private String useName;
    private String chatText;

    public LiveChatBean() {
    }

    public LiveChatBean(int chatType, String useName, String chatText) {
        this.chatType = chatType;
        this.useName = useName;
        this.chatText = chatText;
    }

    public int getChatType() {
        return chatType;
    }

    public void setChatType(int chatType) {
        this.chatType = chatType;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }
}
