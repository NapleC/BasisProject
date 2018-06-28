package com.dxs.stc.adpater.stickyheader;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;

import java.util.List;

/**
 * created by hl at 2018/6/28
 * com.dxs.stc.adpater.stickyheader.TestAdapter
 *
 * @version V1.0 <描述当前版本功能>
 */
public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TestAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String s) {

        helper.setText(R.id.tv_trading_number,
                String.format(mContext.getString(R.string.trading_number), "201806191500330003 ") + s);
//        TextView mSplitTv = helper.getView(R.id.tv_type);
//        mSplitTv.setText("商品名称：");
    }
}
