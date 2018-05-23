package com.dxs.stc.adpater;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.mvp.bean.MallTopicBean;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

import java.util.List;

/**
 * created by hl at 2018/5/22
 * MallHeaderProductsAdapter
 * 商城fragment列表 头部的新品展示列表适配器
 */
public class MallHeaderTopicAdapter extends BaseQuickAdapter<MallTopicBean, BaseViewHolder> {

    public MallHeaderTopicAdapter(int layoutResId, @Nullable List<MallTopicBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallTopicBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        ImageLodeUtils.loadDrawable(mContext, item.getTopicDrawable(),
                helper.getView(R.id.iv_cover));
    }
}
