package com.dxs.stc.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.dxs.stc.R;

/**
 * created by hl at 2018/5/26
 * com.naple.flaxtabtest.widget.NiftyDialog
 *
 * @version V1.0 弹出对话框
 * @author https://github.com/xiaoxiaoqingyi/mine-android-repository
 * <p>
 * new NiftyDialog(this, R.style.dialog,"content text", new NiftyDialog.OnCloseListener() {
 *     @Override
 *     public void onClick(Dialog dialog, boolean confirm) {
 *         if (confirm) {
 *             Do SomeThing
 *         }
 *     }
 * }).show();
 * </p>
 **/
public class NiftyDialog extends Dialog implements View.OnClickListener {

    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;

    int themeResId = R.style.dialog;

    public NiftyDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public NiftyDialog(@NonNull Context context, String content) {
        super(context);
        this.mContext = context;
        this.content = content;
    }


    public NiftyDialog(@NonNull Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public NiftyDialog(@NonNull Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected NiftyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public NiftyDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public NiftyDialog setPositiveName(String positiveName) {
        this.positiveName = positiveName;
        return this;
    }

    public NiftyDialog setNegativeName(String negativeName) {
        this.negativeName = negativeName;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        contentTxt = (TextView) findViewById(R.id.content);
        titleTxt = (TextView) findViewById(R.id.title);
        submitTxt = (TextView) findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = (TextView) findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);

        contentTxt.setText(content);
        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }

        titleTxt.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancel:
                if (listener != null) {
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                this.dismiss();
                break;
        }
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

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}
