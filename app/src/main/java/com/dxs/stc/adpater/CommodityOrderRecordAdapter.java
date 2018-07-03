package com.dxs.stc.adpater;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

import java.util.List;

/**
 * created by hl at 2018/7/2
 * CommodityOrderRecordAdapter
 * 订单适配器
 */
public class CommodityOrderRecordAdapter extends BaseQuickAdapter<Movie.SubjectsBean, BaseViewHolder> {

    public CommodityOrderRecordAdapter(int layoutResId, @Nullable List<Movie.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie.SubjectsBean item) {

        TextView mPriceTv = helper.getView(R.id.tv_total);
        TextView mAuctionDealPriceTv = helper.getView(R.id.tv_auction_deal_price);
        helper.setText(R.id.tv_title, item.getTitle() +
                item.getOriginal_title() + "(" + item.getYear() + ")");
        helper.setText(R.id.tv_unit_price, item.getYear() +
                " " + mContext.getString(R.string.the_price_name));
        helper.setText(R.id.tv_num, "X1");



        SpanUtil.create()
                .addSection(" " + mContext.getString(R.string.knot_price))
                .addForeColorSection(item.getYear(),
                        ContextCompat.getColor(mContext, R.color.price_color))
                .showIn(mPriceTv);

        ImageLodeUtils.loadingRoundImage(mContext, item.getImages().getSmall(),
                helper.getView(R.id.giv_order_pic));

    }
}
