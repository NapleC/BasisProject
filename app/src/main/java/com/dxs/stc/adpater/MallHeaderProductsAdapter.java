package com.dxs.stc.adpater;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.base.Constant;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

import java.util.List;

/**
 * created by hl at 2018/5/22
 * MallHeaderProductsAdapter
 * 商城fragment列表 头部的新品展示列表适配器
 */
public class MallHeaderProductsAdapter extends BaseQuickAdapter<Movie.SubjectsBean, BaseViewHolder> {

    public MallHeaderProductsAdapter(int layoutResId, @Nullable List<Movie.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie.SubjectsBean item) {
        helper.setText(R.id.tv_title, item.getTitle() + item.getOriginal_title() + "(" + item.getYear() + ")");
        SpanUtil.create()
                .addSection(" "+item.getYear())
                .setAbsSize(item.getYear(),16)
                .addSection(mContext.getString(R.string.the_price_name))
                .showIn(helper.getView(R.id.tv_price));
        ImageLodeUtils.loadingImage(mContext, Constant.tempImg, helper.getView(R.id.iv_cover));
    }
}
