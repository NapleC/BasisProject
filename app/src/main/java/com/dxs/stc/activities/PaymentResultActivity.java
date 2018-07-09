package com.dxs.stc.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.base.Constant;
import com.dxs.stc.dialog.EnterTransactionPwdDialog;
import com.dxs.stc.dialog.NiftyDialog;
import com.dxs.stc.utils.AppManager;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.SPUtil;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PaymentResultActivity extends CompatStatusBarActivity {

    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;

    @BindView(R.id.tv_payment_result)
    TextView mPaymentResult;
    @BindView(R.id.btn_check_out)
    Button mCheckOutBtn;
    @BindView(R.id.btn_looking_around)
    Button mLookingAroundBtn;

    EnterTransactionPwdDialog mDialog;
    private boolean isSuccessStatus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_result);

        getWindow().setBackgroundDrawableResource(R.color.white);
        ButterKnife.bind(this);
        setStatus(true, true, ContextCompat.getColor(this, R.color.navColor));
    }

    @OnClick({R.id.iv_bar_left,R.id.tv_bar_text, R.id.btn_check_out, R.id.btn_looking_around, R.id.tv_payment_result})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_left:
                onBackPressed();
                Loger.debug("栈顶的activity"+ AppManager.getInstance().getTopActivity());
                break;
            case R.id.btn_check_out:
                ToastUtils.showShort("查看订单");
                Intent orderIntent = new Intent(PaymentResultActivity.this, OrderRecordActivity.class);
                orderIntent.putExtra(Constant.ORDER_TYPE, Constant.ORDER_AUCTION);
                orderIntent.putExtra(Constant.ORDER_STATUS, 1);
                startActivity(orderIntent);
                finish();
                break;
            case R.id.btn_looking_around:
                ToastUtils.showShort("继续逛逛");
                Intent shopIntent = new Intent(PaymentResultActivity.this, MainActivity.class);
                startActivity(shopIntent);
//                finish();
                break;
            case R.id.tv_bar_text:
                isSuccessStatus = !isSuccessStatus;
                setPaymenStatus();
                break;
            case R.id.tv_payment_result:
                showDialog();
                break;
        }
    }

    private void setPaymenStatus() {
        mCheckOutBtn.setVisibility(isSuccessStatus ? View.VISIBLE : View.INVISIBLE);
        mLookingAroundBtn.setVisibility(isSuccessStatus ? View.VISIBLE : View.INVISIBLE);
        if (isSuccessStatus) {

            mPaymentResult.setText("你已支付成功");
        } else {
            SpanUtil.create()
                    .addSection("支付失败\n")
                    .addForeColorSection("您的" +
                                    PaymentResultActivity.this.getString(R.string.the_price_name) + "不足，",
                            ContextCompat.getColor(PaymentResultActivity.this, R.color.color_66))
                    .addForeColorSection("请前往充值",
                            ContextCompat.getColor(PaymentResultActivity.this, R.color.price_color))
                    .setUnderline("请前往充值")
                    .showIn(mPaymentResult);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {

        mTitleCenter.setText(getString(R.string.payment_result));
        setPaymenStatus();

        mDialog = new EnterTransactionPwdDialog(PaymentResultActivity.this, R.style.dialog);
        mDialog.setOnCompleteListener(new EnterTransactionPwdDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean isForget, boolean isComplete) {

                if (isForget){
                    startActivity(new Intent(PaymentResultActivity.this,SetTransactionPasswordActivity.class));
                } else if (isComplete){
                    mDialog.showIncorrectPwdPrompt(true);
                }
            }
        });
    }

    private void showDialog(){

        if (mDialog==null){
            mDialog = new EnterTransactionPwdDialog(PaymentResultActivity.this, R.style.dialog);
        }
        mDialog.show();
    }
}
