package com.dxs.stc.adpater;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

import java.util.List;

/**
 * created by hl at 2018/5/16
 * AuctionRecyclerViewAdapter
 */
public class AuctionRecyclerViewAdapter extends BaseQuickAdapter<Movie.SubjectsBean, BaseViewHolder> {

    public AuctionRecyclerViewAdapter(int layoutResId, @Nullable List<Movie.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie.SubjectsBean item) {
        TextView mTopTip = helper.getView(R.id.tv_top_tip);
        TextView mBottomTitle = helper.getView(R.id.tv_bottom_title);
        TextView mWatchMore = helper.getView(R.id.tv_watch_this_space);
        mTopTip.setVisibility(
                helper.getLayoutPosition() == (getData().size()) ? View.GONE : View.VISIBLE);
        mBottomTitle.setVisibility(
                helper.getLayoutPosition() == (getData().size()) ? View.GONE : View.VISIBLE);
        mWatchMore.setVisibility(
                helper.getLayoutPosition() == (getData().size()) ? View.VISIBLE : View.GONE);
        mBottomTitle.setText(String.format(mContext.getResources().getString(R.string.auction_title),
                item.getTitle() + item.getOriginal_title() + "(" + item.getYear() + ")"));
        ImageLodeUtils.loadingImage(mContext, item.getImages().getSmall(),
                helper.getView(R.id.iv_auction_cover));
    }
}
