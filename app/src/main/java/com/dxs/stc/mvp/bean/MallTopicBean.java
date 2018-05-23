package com.dxs.stc.mvp.bean;

import com.dxs.stc.R;

import java.io.Serializable;

/**
 * created by hl at 2018/5/23
 * com.dxs.stc.mvp.bean.MallTopicBean
 *
 * @version V1.0 <描述当前版本功能>
 */
public class MallTopicBean implements Serializable {

    private String id = "12475";
    private String title = "手镯";
    private int topicDrawable = R.mipmap.ic_mall_badge;

    public MallTopicBean() {
    }

    public MallTopicBean(String id, String title, int topicDrawable) {
        this.id = id;
        this.title = title;
        this.topicDrawable = topicDrawable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTopicDrawable() {
        return topicDrawable;
    }

    public void setTopicDrawable(int topicDrawable) {
        this.topicDrawable = topicDrawable;
    }
}
