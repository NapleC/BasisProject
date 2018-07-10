package com.dxs.stc.adpater;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.base.Constant;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

import java.util.List;

/**
 *  created by hl at 2018/7/10
 *  AuctionHistoryAdapter
 *  拍卖历史
 */
public class AuctionHistoryAdapter extends BaseQuickAdapter<Movie.SubjectsBean, BaseViewHolder> {

    public AuctionHistoryAdapter(int layoutResId, @Nullable List<Movie.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie.SubjectsBean item) {
        TextView mTopTip = helper.getView(R.id.tv_top_tip);
        TextView mBottomTitle = helper.getView(R.id.tv_bottom_title);
        TextView mWatchMore = helper.getView(R.id.tv_watch_this_space);
        mTopTip.setText(mContext.getResources().getText(R.string.playback));
        mBottomTitle.setText(String.format(mContext.getResources().getString(R.string.auction_title),
                item.getTitle() + item.getOriginal_title() + "(" + item.getYear() + ")"));
        ImageLodeUtils.loadingImage(mContext, Constant.tempImg,
                helper.getView(R.id.iv_auction_cover));
    }
}
