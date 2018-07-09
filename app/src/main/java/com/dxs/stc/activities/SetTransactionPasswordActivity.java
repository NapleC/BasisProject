package com.dxs.stc.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.widget.PasswordEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.graphics.Typeface.BOLD;

public class SetTransactionPasswordActivity extends CompatStatusBarActivity {

    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;
    @BindView(R.id.tv_top_set)
    TextView mSetTopTv;
    @BindView(R.id.tv_bottom_set)
    TextView mSetBottomTv;
    @BindView(R.id.pet_pwd)
    PasswordEditText mPwdEt;
    @BindView(R.id.btn_set_complete)
    Button mCompleteBtn;

    private int setStep = 1;
    private String newPwd;
    private String confirmPwd;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_transaction_password);

        getWindow().setBackgroundDrawableResource(R.color.white);
        ButterKnife.bind(this);
        setStatus(true, true, ContextCompat.getColor(this, R.color.navColor));
    }

    @OnClick({R.id.iv_bar_left, R.id.btn_set_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_left:
                onBackPressed();
                break;
            case R.id.btn_set_complete:
                if (TextUtils.equals(newPwd, confirmPwd)) {
                    ToastUtils.showShort("修改后的支付密码是：" + mPwdEt.getText().toString());
                } else {
                    thirdInputPwdAgain(true);
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        firstSendCode();

        mTitleCenter.setText(getString(R.string.transaction_password));
        mPwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Loger.debug("s: " + s.toString());
                switch (setStep) {
                    case 1:
                        if (s.toString().length() == 6) {
                            ToastUtils.showShort("验证码校验成功");
                            setStep = 2;
                            secondSetPwd();
                        }
                        break;
                    case 2:
                        if (s.toString().length() == 6) {
                            ToastUtils.showShort("验证码校验成功");
                            newPwd = s.toString();
                            Loger.debug("newPwd: " + newPwd);
                            setStep = 3;
                            thirdInputPwdAgain(false);
                        }
                        break;
                    case 3:

                        if (s.toString().length() == 6) {
                            confirmPwd = s.toString();
                            Loger.debug("confirmPwd: " + confirmPwd);
                            mCompleteBtn.setEnabled(true);
                            mCompleteBtn.setSelected(true);
                        } else {
                            mCompleteBtn.setEnabled(false);
                            mCompleteBtn.setSelected(false);
                        }
                        break;
                }

            }
        });
    }

    private void firstSendCode() {
        showSoftInputFromWindow(SetTransactionPasswordActivity.this, mPwdEt);
        mPwdEt.setText("");
        SpanUtil.create()
                .addSection("我们已发送")
                .addStyleSection("验证码", BOLD)
                .addSection("到你的手机号\n")
                .addSection("130****0000")
                .showIn(mSetTopTv);

        SpanUtil.create()
                .addForeColorSection("重发信息",
                        ContextCompat.getColor(SetTransactionPasswordActivity.this, R.color.mainColor))
                .setStyle(BOLD)
                .showIn(mSetBottomTv);
        mCompleteBtn.setVisibility(View.INVISIBLE);
        mSetBottomTv.setVisibility(View.VISIBLE);
    }

    private void secondSetPwd() {
        mPwdEt.setText("");
        SpanUtil.create()
                .addSection("请为你的账户\n")
                .addStyleSection("重新设置6位数的支付密码", BOLD)
                .showIn(mSetTopTv);

        SpanUtil.create()
                .addSection("支付密码不能与之前设置的 6 位数密码重复")
                .showIn(mSetBottomTv);
        mCompleteBtn.setVisibility(View.INVISIBLE);
        mSetBottomTv.setVisibility(View.VISIBLE);
    }

    private void thirdInputPwdAgain(boolean isWrong) {
        mPwdEt.setText("");
        SpanUtil.create()
                .addSection("请再次输入新设置的密码")
                .showIn(mSetTopTv);
        mCompleteBtn.setVisibility(View.VISIBLE);
        if (isWrong) {

            SpanUtil.create()
                    .addSection("两次密码不一致，请重新输入！")
                    .showIn(mSetBottomTv);
            mSetBottomTv.setVisibility(View.VISIBLE);
        } else {
            mSetBottomTv.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
