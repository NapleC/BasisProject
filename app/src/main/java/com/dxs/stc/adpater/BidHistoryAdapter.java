package com.dxs.stc.adpater;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.activities.LiveRoomActivity;
import com.dxs.stc.bean.BidHistoryBean;
import com.dxs.stc.utils.SpanUtil;

import java.util.List;

/**
 *  created by hl at 2018/7/5
 *  BidHistoryAdapter
 *  出价历史
 */
public class BidHistoryAdapter extends BaseQuickAdapter<BidHistoryBean, BaseViewHolder> {

    public BidHistoryAdapter(int layoutResId, List<BidHistoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BidHistoryBean bean) {
        TextView mText = helper.getView(R.id.tv_bid_history);

        SpanUtil.create()
                .clearSpans()
                .addForeColorSection("["+bean.getBidTime()+"] ",
                        ContextCompat.getColor(mContext, R.color.mainColor))
                .addSection(bean.getUseName() + " 出价 ")
                .addForeColorSection(bean.getBidPrice() + mContext.getString(R.string.the_price_name),
                        ContextCompat.getColor(mContext, R.color.price_color))
                .showIn(mText);
    }
}