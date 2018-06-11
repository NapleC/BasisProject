package com.dxs.stc.adpater;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

import java.util.List;

@Deprecated
public class MallListAdapter extends BaseQuickAdapter<Movie.SubjectsBean, BaseViewHolder> {

    public MallListAdapter(int layoutResId, @Nullable List<Movie.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie.SubjectsBean item) {

        TextView mPriceTv = helper.getView(R.id.tv_price);

        helper.setText(R.id.tv_title, item.getTitle() +
                item.getOriginal_title() + "(" + item.getYear() + ")");
        helper.setText(R.id.tv_count, item.getCollect_count() + "人购买");

        SpanUtil.create()
                .addForeColorSection(item.getYear(),
                        ContextCompat.getColor(mContext, R.color.price_color))
                .setAbsSize(item.getYear(), 16)
                .addSection(" " + mContext.getString(R.string.the_price_name))
                .showIn(mPriceTv);

        ImageLodeUtils.loadingRoundImage(mContext, item.getImages().getSmall(),
                helper.getView(R.id.iv_cover));
        helper.getView(R.id.tv_free_shipping).setVisibility(
                helper.getLayoutPosition() % 2 == 0 ? View.VISIBLE : View.GONE);

    }
}
