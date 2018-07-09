package com.dxs.stc.adpater;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.base.Constant;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

import java.util.List;

/**
 *  created by hl at 2018/7/2
 *  AuctionRecordAdapter
 *  拍卖订单适配器
 */
public class AuctionRecordAdapter extends BaseQuickAdapter<Movie.SubjectsBean, BaseViewHolder> {

    public AuctionRecordAdapter(int layoutResId, @Nullable List<Movie.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie.SubjectsBean item) {

        TextView mPriceTv = helper.getView(R.id.tv_knot_price);
        helper.setText(R.id.tv_title, item.getTitle() +
                item.getOriginal_title() + "(" + item.getYear() + ")");
        helper.setText(R.id.tv_knot_beat_time, mContext.getString(R.string.knot_beat_time) + item.getCollect_count());

        SpanUtil.create()
                .addSection(" " + mContext.getString(R.string.knot_price))
                .addForeColorSection(item.getYear(),
                        ContextCompat.getColor(mContext, R.color.price_color))
                .showIn(mPriceTv);

        ImageLodeUtils.loadingRoundImage(mContext, Constant.tempImg,
                helper.getView(R.id.iv_image));

    }
}
