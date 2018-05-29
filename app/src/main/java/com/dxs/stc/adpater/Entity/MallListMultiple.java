package com.dxs.stc.adpater.Entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * created by hl at 2018/5/28
 * com.dxs.stc.adpater.Entity.MallListMultiple
 *
 * @version V1.0 商城列表两种布局展示
 */
public class MallListMultiple implements MultiItemEntity {
    public static final int LIST = 1;
    public static final int GRID = 2;
    private int itemType;

    public MallListMultiple(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
