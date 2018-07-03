package com.dxs.stc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailsActivity extends CompatStatusBarActivity {

    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;

    @BindView(R.id.tv_top_status)
    TextView mTopStatusTv;
    @BindView(R.id.tv_top_status_tip)
    TextView mTopStatusTipTv;

    @BindView(R.id.tv_name)
    TextView mNameTv;
    @BindView(R.id.tv_phone)
    TextView mPhoneTv;
    @BindView(R.id.tv_address_details)
    TextView mAddressDetailsTv;
    @BindView(R.id.iv_more)
    ImageView mMoreIv;

    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_unit_price)
    TextView mUnitPriceTv;
    @BindView(R.id.tv_count)
    TextView mCountTv;
    @BindView(R.id.tv_product_total)
    TextView mProductTotalTv;
    @BindView(R.id.tv_real_total)
    TextView mRealTotalTv;

    @BindView(R.id.tv_order_info)
    TextView mOrderInfoTv;

    @BindView(R.id.btn_order_left)
    Button mOrderLeft;
    @BindView(R.id.btn_order_right)
    Button mOrderRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        ButterKnife.bind(this);
        setStatus(true, true,
                ContextCompat.getColor(OrderDetailsActivity.this, R.color.navColor));
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        mTitleCenter.setText(getString(R.string.order_details));

        mNameTv.setText("张三");
        mPhoneTv.setText("130****0000");
        mAddressDetailsTv.setText("广东省深圳市福田区 XXXXXXXXXXXXXXX 街道 XXXX大厦 2002");
        mTitleTv.setText("翡翠珠宝深绿色耳坠价值千金翡翠珠宝深绿色耳坠价");

        SpanUtil.create()
                .addSection("起拍价：")
                .addSection("12000 "+this.getResources().getString(R.string.the_price_name))
                .setAbsSize("12000 "+this.getResources().getString(R.string.the_price_name), 16)
                .showIn(mUnitPriceTv);

        SpanUtil.create()
                .addSection("成交价：")
                .addForeColorSection("12000 "+this.getResources().getString(R.string.the_price_name),
                        ContextCompat.getColor(OrderDetailsActivity.this, R.color.price_color))
                .setAbsSize("12000 "+this.getResources().getString(R.string.the_price_name), 16)
                .showIn(mProductTotalTv);

        SpanUtil.create()
                .addSection("共 1 件商品 合计：")
                .addSection("12000 "+this.getResources().getString(R.string.the_price_name))
                .setAbsSize("12000 "+this.getResources().getString(R.string.the_price_name), 16)
                .showIn(mRealTotalTv);


        SpanUtil.create()
                .addSection("订单编号: " + "201805051705361123456" + "\n")
                .addSection("开拍时间: " + "2018-05-05 15：30" + "\n")
                .addSection("落槌时间: " + "2018-05-05 15：40" + "\n")
                .addSection("成交时间: " + "2018-05-05 18：00" + "\n")
                .addSection("发货时间: " + "2018-05-05 19：30" + "\n")
                .addSection("配送方式: " + "顺丰" + "\n")
                .addSection("快递单号: " + "123456789222" + "\n")
                .showIn(mOrderInfoTv);

        mOrderInfoTv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @OnClick({R.id.iv_bar_left, R.id.rl_order_address, R.id.btn_order_left, R.id.btn_order_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_left:
                onBackPressed();
                break;
            case R.id.rl_order_address:
                startActivity(new Intent(OrderDetailsActivity.this, AddressManagerActivity.class));
                break;
            case R.id.btn_order_left:
                ToastUtils.showShort("点击了左边的按钮");
                break;
            case R.id.btn_order_right:
                ToastUtils.showShort("点击了右边的按钮");
                break;
        }
    }
}
