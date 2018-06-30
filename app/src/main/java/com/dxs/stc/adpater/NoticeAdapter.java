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
 * created by hl at 2018/6/30
 * NoticeAdapter
 * 预告列表适配器
 */
public class NoticeAdapter extends BaseQuickAdapter<Movie.SubjectsBean, BaseViewHolder> {

    private String tempImg = "https://image2.wbiao.co/upload/article/201702/17/1487322373441497976.jpg";

    public NoticeAdapter(int layoutResId, @Nullable List<Movie.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie.SubjectsBean item) {
        TextView mTimeTv = helper.getView(R.id.tv_notice_time);
        helper.setText(R.id.tv_notice_tip, "预告篇 " + helper.getLayoutPosition());
        helper.setText(R.id.tv_notice_title, "这是一个公告标题，啦啦啦啦啦 ");
        ImageLodeUtils.loadingImage(mContext, tempImg, helper.getView(R.id.iv_notice_pic));

        SpanUtil.create()
                .addSection(" " + mContext.getString(R.string.auction_start_time))
                .addForeColorSection(item.getYear(),
                        ContextCompat.getColor(mContext, R.color.mainColor))
                .showIn(mTimeTv);
    }
}
