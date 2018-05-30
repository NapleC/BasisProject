package com.dxs.stc.adpater;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

import java.util.List;


public class MallGridAdapter extends BaseQuickAdapter<Movie.SubjectsBean, BaseViewHolder> {

    public MallGridAdapter(int layoutResId, @Nullable List<Movie.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie.SubjectsBean item) {
        helper.setText(R.id.tv_title, item.getTitle() +
                item.getOriginal_title() + "(" + item.getYear() + ")");
        helper.setText(R.id.tv_count, item.getCollect_count() + "人购买");
        helper.setText(R.id.tv_price, item.getYear());
        ImageLodeUtils.loadingImage(mContext, item.getImages().getSmall(), helper.getView(R.id.iv_cover));
        helper.getView(R.id.tv_free_shipping).setVisibility(
                helper.getLayoutPosition() % 2 == 0 ? View.VISIBLE : View.GONE);

    }
}
