package com.dxs.stc.adpater;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

import java.util.List;

/**
 * created by hl at 2018/5/14
 * com.naple.hldemo.adpater
 */
public class HomeRecyclerViewAdapter extends BaseQuickAdapter<Movie.SubjectsBean, BaseViewHolder> {

    public HomeRecyclerViewAdapter(int layoutResId, @Nullable List<Movie.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Movie.SubjectsBean item) {
        helper.setText(R.id.tv_title, item.getTitle()+item.getOriginal_title()+"("+item.getYear()+")");
        helper.setText(R.id.tv_directors, item.getTitle()+item.getOriginal_title()+"("+item.getYear()+")");

        StringBuffer directorsBuffer = new StringBuffer("导演：");
        for (int j = 0,lenD = item.getDirectors().size(); j < lenD; j++) {
            directorsBuffer.append(item.getDirectors().get(j).getName()+"、");
        }
        directorsBuffer.deleteCharAt(directorsBuffer.length()-1);
        helper.setText(R.id.tv_directors, directorsBuffer.toString());
        directorsBuffer.setLength(0);

        StringBuffer castsBuffer = new StringBuffer("主演：");
        for (int i = 0,lenC = item.getCasts().size(); i < lenC; i++) {
            castsBuffer.append(item.getCasts().get(i).getName()+"、");
        }
        castsBuffer.deleteCharAt(castsBuffer.length()-1);
        helper.setText(R.id.tv_casts, castsBuffer.toString());
        castsBuffer.setLength(0);
        ImageLodeUtils.loadingImage(mContext, item.getImages().getSmall(), helper.getView(R.id.iv_cover));
    }
}
