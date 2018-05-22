package com.dxs.stc.adpater;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.mvp.bean.Movie;

import java.util.List;

/**
 * created by hl at 2018/5/19
 * AuctionHeaderSiteAdapter
 * 拍卖fragment列表 头部的预告列表适配器
 */
public class AuctionHeaderSiteAdapter extends BaseQuickAdapter<Movie.SubjectsBean, BaseViewHolder> {

    public AuctionHeaderSiteAdapter(int layoutResId, @Nullable List<Movie.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie.SubjectsBean item) {
        helper.setText(R.id.tv_header_site_tip, "预告篇 " + helper.getLayoutPosition());
//        ImageLodeUtils.loadingImage(mContext, item.getImages().getSmall(),
//                helper.getView(R.id.iv_header_site_cover));
    }
}
