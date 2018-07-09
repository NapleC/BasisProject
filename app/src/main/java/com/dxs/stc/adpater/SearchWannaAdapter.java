package com.dxs.stc.adpater;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;

import java.util.List;

/**
 *  created by hl at 2018/7/5
 *  SearchWannaAdapter
 *  搜索页面，你想搜索 标签的适配器
 */
public class SearchWannaAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SearchWannaAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {
        helper.setText(R.id.text, s);
    }
}