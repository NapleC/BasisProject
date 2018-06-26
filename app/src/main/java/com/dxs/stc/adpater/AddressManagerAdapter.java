package com.dxs.stc.adpater;

import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dxs.stc.R;
import com.dxs.stc.bean.AddressBean;
import com.dxs.stc.utils.Loger;

import java.util.List;

/**
 * created by hl at 2018/6/25
 * AddressManagerAdapter
 * 地址管理列表
 */
public class AddressManagerAdapter extends BaseQuickAdapter<AddressBean, BaseViewHolder> {

    private CheckItemListener mCheckListener;

    public AddressManagerAdapter(int layoutResId, List<AddressBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressBean addressBean) {
        //
        helper.setText(R.id.tv_name, addressBean.getUserName());
        helper.setText(R.id.tv_phone, addressBean.getUserPhone());
        helper.setText(R.id.tv_address_details,
                addressBean.getUserRegion() + addressBean.getFullAddress());

        CheckBox mCheckBox = helper.getView(R.id.cb_is_default);
        mCheckBox.setChecked(addressBean.isDefault());
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loger.debug("isChecked : " + mCheckBox.isChecked());
                Loger.debug("position : " + helper.getAdapterPosition());
                if (getCheckItemListener() != null)
                    mCheckListener.itemChecked(helper.getAdapterPosition());
            }
        });
    }


    public final CheckItemListener getCheckItemListener() {
        return mCheckListener;
    }

    // 自定义监听第二步
    public void setCheckItemListener(CheckItemListener mCheckListener) {
        this.mCheckListener = mCheckListener;
    }

    // 自定义监听第一步
    public interface CheckItemListener {
        void itemChecked(int position);
    }
}