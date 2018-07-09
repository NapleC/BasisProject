package com.dxs.stc.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.activities.BannerActivity;
import com.dxs.stc.glideimageview.GlideImageView;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.widget.PasswordEditText;

/**
 *  created by hl at 2018/7/9
 *  AuctionResultDialog
 *  拍卖结果页面
 */
public class AuctionResultDialog extends Dialog implements View.OnClickListener {

    private ImageView mCloseIv;
    private TextView mResultTopTv;
    private GlideImageView mAuctionPic;
    private TextView mAuctionTitleTv;
    private TextView mAuctionPriceTv;
    private Button mToPayBtn;
    private Button mSaveScreenshotBtn;

    private Context mContext;
    private OnDialogListener listener;

    public AuctionResultDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }


    public AuctionResultDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public AuctionResultDialog(@NonNull Context context, int themeResId, OnDialogListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_auction_result);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mCloseIv = (ImageView) findViewById(R.id.iv_close);
        mResultTopTv = (TextView) findViewById(R.id.tv_result_top);
        mAuctionPic = (GlideImageView) findViewById(R.id.iv_result_pic);
        mAuctionTitleTv = (TextView) findViewById(R.id.tv_result_title);
        mAuctionPriceTv = (TextView) findViewById(R.id.tv_result_price);
        mToPayBtn = (Button) findViewById(R.id.btn_to_pay);
        mSaveScreenshotBtn = (Button) findViewById(R.id.btn_save_screenshot);

        mCloseIv.setOnClickListener(this);
        mToPayBtn.setOnClickListener(this);
        mSaveScreenshotBtn.setOnClickListener(this);


        SpanUtil.create()
                .addSection("恭喜你")
                .addForeColorSection("<xzxxxxxx>", ContextCompat.getColor(mContext,R.color.mainColor))
                .addSection(",竞拍成功\n")
                .addSection("当前竞拍价触发最高价，竞拍结束")
                .setAbsSize("当前竞拍价触发最高价，竞拍结束", 12)
                .showIn(mResultTopTv);
        mAuctionTitleTv.setText("1.翡翠交易所深绿色原宝石2.深绿色原宝石3.翡翠交易所深绿色原宝石4.深绿色原宝石");
        mAuctionPriceTv.setText("成交价 26000000 STC");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_close:
                this.dismiss();
                break;
            case R.id.btn_to_pay:
                ToastUtils.showShort("点击支付~");
                break;
            case R.id.btn_save_screenshot:
                ToastUtils.showShort("截屏");
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

    public void setOnCompleteListener(OnDialogListener listener) {
        this.listener = listener;
    }

    public interface OnDialogListener {
        void onClick(Dialog dialog, boolean isComplete);
    }

}
