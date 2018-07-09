package com.dxs.stc.adpater.Entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * created by hl at 2018/5/28
 * com.dxs.stc.adpater.Entity.LiveChatMultipleItem
 *
 * @version V1.0 商城列表两种布局展示
 */
public class LiveChatMultipleItem implements MultiItemEntity {
    public static final int OTHER = 0;
    public static final int MINE = 1;
    private int itemType;

    public LiveChatMultipleItem(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
