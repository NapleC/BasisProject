package com.dxs.stc.adpater;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

import java.util.List;

/**
 * created by hl at 2018/5/19
 * AuctionHeaderSiteAdapter
 * 拍卖fragment列表 头部的预告列表适配器
 */
public class AuctionHeaderSiteAdapter extends BaseQuickAdapter<Movie.SubjectsBean, BaseViewHolder> {

    private String tempImg = "https://image2.wbiao.co/upload/article/201702/17/1487322373441497976.jpg";

    public AuctionHeaderSiteAdapter(int layoutResId, @Nullable List<Movie.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie.SubjectsBean item) {
        helper.setText(R.id.tv_header_site_tip, "预告篇 " + helper.getLayoutPosition());
        ImageLodeUtils.loadingImage(mContext, tempImg, helper.getView(R.id.iv_header_site_cover));
    }
}
