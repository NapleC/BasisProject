package com.dxs.stc.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.base.Constant;
import com.dxs.stc.dialog.EnterTransactionPwdDialog;
import com.dxs.stc.glideimageview.GlideImageView;
import com.dxs.stc.utils.AppManager;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SalesConfirmationActivity extends CompatStatusBarActivity {

    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;

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
    EnterTransactionPwdDialog mDialog;

    private String orderType;
    private Activity needActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_confirmation);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        ButterKnife.bind(this);
        setStatus(true, true,
                ContextCompat.getColor(SalesConfirmationActivity.this, R.color.navColor));
        getIntentData();
    }

    private void getIntentData() {

        Intent intent = getIntent();
        orderType = intent.getStringExtra(Constant.ORDER_TYPE);
        Loger.debug("orderType:" + orderType);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        mTitleCenter.setText(getString(R.string.sales_confirmation));
        mDistributionWay.setText("顺丰");
        mProdTitle.setText("天然翡翠手镯女玉镯子缅甸玉手镯女款冰糯种飘花玉石");
        SpanUtil.create()
                .addSection(" " + mUnitPrice)
                .setAbsSize(" " + mUnitPrice, 16)
                .addSection(SalesConfirmationActivity.this.getString(R.string.the_price_name))
                .showIn(mUnitPriceTv);
        setChangeText();


        mDialog = new EnterTransactionPwdDialog(SalesConfirmationActivity.this, R.style.dialog);
        mDialog.setOnCompleteListener(new EnterTransactionPwdDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean isForget, boolean isComplete) {
                dialog.dismiss();
                if (isForget) {
                    startActivity(new Intent(SalesConfirmationActivity.this, SetTransactionPasswordActivity.class));
                } else if (isComplete) {
                    startActivity(new Intent(SalesConfirmationActivity.this, PaymentResultActivity.class));

                    if (TextUtils.equals(orderType, Constant.ORDER_MALL)) {
                        needActivity = AppManager.getInstance().getActivity(BannerActivity.class);
                    } else {
                        needActivity = AppManager.getInstance().getActivity(LiveRoomActivity.class);
                    }
                    needActivity.finish();
                    Loger.debug("获取指定的activity:" + AppManager.getInstance().getActivity(BannerActivity.class));
                    SalesConfirmationActivity.this.finish();
                }
            }
        });
    }

    private void showDialog() {

        if (mDialog == null) {
            mDialog = new EnterTransactionPwdDialog(SalesConfirmationActivity.this, R.style.dialog);
        }
        mDialog.show();
    }

    @OnClick({R.id.iv_bar_left, R.id.rl_address, R.id.btn_decrease,
            R.id.btn_increase, R.id.btn_confirm_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_left:
                onBackPressed();
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

                showDialog();
                break;
        }
    }


    @SuppressLint("DefaultLocale")
    private void setChangeText() {

        mTotalAmount = prodNum * mUnitPrice;
        mProdNumTv.setText(String.format("x%d", prodNum));
        mNumberTv.setText(String.format("%d", prodNum));


        SpanUtil.create()
                .addSection(SalesConfirmationActivity.this.getString(R.string.total_amount))
                .addForeColorSection(" " + mTotalAmount,
                        ContextCompat.getColor(SalesConfirmationActivity.this, R.color.price_color))
                .setAbsSize(" " + mTotalAmount, 18)
                .addForeColorSection(SalesConfirmationActivity.this.getString(R.string.the_price_name),
                        ContextCompat.getColor(SalesConfirmationActivity.this, R.color.price_color))
                .showIn(mTotalAmountTv);
    }
}
