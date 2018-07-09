package com.dxs.stc.adpater;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;

import java.util.List;

/**
 *  created by hl at 2018/7/5
 *  SearchHistoryAdapter
 *  搜索页面，搜索历史 标签的适配器
 */
public class SearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SearchHistoryAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {
        helper.setText(R.id.text, s);
    }
}