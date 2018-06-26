package com.dxs.stc.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.glideimageview.GlideImageView;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SalesConfirmationActivity extends CompatStatusBarActivity {

    @BindView(R.id.iv_bar_left)
    ImageView mTitleLeft;
    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;
    @BindView(R.id.iv_bar_right)
    ImageView mTitleRight;

    @BindView(R.id.rl_address)
    RelativeLayout mAddressArea;
    @BindView(R.id.giv_prod_pic)
    GlideImageView mProdPic;
    @BindView(R.id.tv_title)
    TextView mProdTitle;
    @BindView(R.id.tv_num)
    TextView mProdNumTv;
    @BindView(R.id.tv_unit_price)
    TextView mUnitPriceTv;
    @BindView(R.id.num)
    TextView mNumberTv;
    @BindView(R.id.tv_distribution)
    TextView mDistributionWay;
    @BindView(R.id.tv_total_amount)
    TextView mTotalAmountTv;

    @BindView(R.id.et_remark)
    EditText mRemarkEt;

    private int prodNum = 1;
    private int mUnitPrice = 2000;
    private float mTotalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        setContentView(R.layout.activity_sales_confirmation);
        ButterKnife.bind(this);
        setStatus(true, true,
                ContextCompat.getColor(SalesConfirmationActivity.this, R.color.navColor));



    }

    @Override
    protected void onStart() {
        super.onStart();
        mTitleCenter.setText(getString(R.string.sales_confirmation));
        mDistributionWay.setText("顺丰");
        mProdTitle.setText("天然翡翠手镯女玉镯子缅甸玉手镯女款冰糯种飘花玉石");
        SpanUtil.create()
                .addSection(SalesConfirmationActivity.this.getString(R.string.the_price_name))
                .addSection(" " + mUnitPrice)
                .setAbsSize(" " + mUnitPrice, 16)
                .showIn(mUnitPriceTv);
        setChangeText();
    }

    @OnClick({R.id.iv_bar_right, R.id.iv_bar_left, R.id.rl_address, R.id.btn_decrease,
            R.id.btn_increase, R.id.btn_confirm_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_right:
                ToastUtils.showShort("点击右边按钮");
                break;
            case R.id.iv_bar_left:
                ToastUtils.showShort("点击左边按钮");
                break;
            case R.id.rl_address:
                startActivity(new Intent(SalesConfirmationActivity.this, AddressManagerActivity.class));
                break;
            case R.id.btn_decrease:
                if (prodNum <= 1) {
                    ToastUtils.showShort("最少 1 件哦~");
                } else {
                    prodNum--;
                    setChangeText();

                }
                break;
            case R.id.btn_increase:
                if (prodNum >= 3) {
                    ToastUtils.showShort("每人每次最多 3 件哦~");
                } else {
                    prodNum++;
                    setChangeText();
                }
                break;
            case R.id.btn_confirm_pay:
                // 确定支付，提交订单~
                Loger.debug("prodNum:" + prodNum);
                Loger.debug("mRemarkEt.Text:" + mRemarkEt.getText());
                Loger.debug("tv_total_amount:" + prodNum * mUnitPrice);
                break;
        }
    }


    @SuppressLint("DefaultLocale")
    private void setChangeText() {

        mTotalAmount = prodNum * mUnitPrice;
        mProdNumTv.setText(String.format("x%d", 1));
        mNumberTv.setText(String.format("%d", 1));

        SpanUtil.create()
                .addSection(SalesConfirmationActivity.this.getString(R.string.total_amount))
                .addForeColorSection(SalesConfirmationActivity.this.getString(R.string.the_price_name),
                        ContextCompat.getColor(SalesConfirmationActivity.this, R.color.price_color))
                .addForeColorSection(" " + mTotalAmount,
                        ContextCompat.getColor(SalesConfirmationActivity.this, R.color.price_color))
                .setAbsSize(" " + mTotalAmount, 18)
                .showIn(mTotalAmountTv);
    }
}
