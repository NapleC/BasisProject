package com.dxs.stc.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.adpater.MallMoreAdapter;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.widget.PasswordEditText;

/**
 * created by hl at 2018/5/26
 * com.naple.flaxtabtest.widget.NiftyDialog
 *
 * @author https://github.com/xiaoxiaoqingyi/mine-android-repository
 * <p>
 * new NiftyDialog(this, R.style.dialog,"content text", new NiftyDialog.OnCloseListener() {
 * @version V1.0 弹出对话框
 * @Override public void onClick(Dialog dialog, boolean confirm) {
 * if (confirm) {
 * Do SomeThing
 * }
 * }
 * }).show();
 * </p>
 **/
public class EnterTransactionPwdDialog extends Dialog implements View.OnClickListener {

    private PasswordEditText mPwdEt;
    private TextView titleTxt;
    private TextView mPwdPrompt;
    private TextView mForgetPwd;

    private Context mContext;
    private OnCloseListener listener;

    public EnterTransactionPwdDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }


    public EnterTransactionPwdDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public EnterTransactionPwdDialog(@NonNull Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_enter_transaction_pwd);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mPwdEt = (PasswordEditText) findViewById(R.id.pet_pwd);
        titleTxt = (TextView) findViewById(R.id.title);
        mPwdPrompt = (TextView) findViewById(R.id.tv_incorrect_pwd_prompt);
        mForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);

        titleTxt.setOnClickListener(this);
        mForgetPwd.setOnClickListener(this);
        showSoftInputFromWindow(mPwdEt);

        mPwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 6) {
                    if (listener != null) {
                        listener.onClick(EnterTransactionPwdDialog.this, false, true);
                    }
                } else {
                    showIncorrectPwdPrompt(false);
                }

            }
        });
    }

    public void showIncorrectPwdPrompt(boolean isIncorrect) {
        mPwdPrompt.setVisibility(isIncorrect ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.title:
                mPwdEt.setText("");
                this.dismiss();
                break;
            case R.id.tv_forget_pwd:
                if (listener != null) {
                    listener.onClick(this, true, false);
                }
                mPwdEt.setText("");
                this.dismiss();
                break;
        }
    }

    public void setOnCompleteListener(OnCloseListener listener) {
        this.listener = listener;
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean isForget, boolean isComplete);
    }



    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
