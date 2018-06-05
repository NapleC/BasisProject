package com.dxs.stc.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.utils.DensityUtils;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

/**
 * created by hl at 2018/5/17
 * com.dxs.stc.widget.CustomTextOnPic
 *
 * @version V1.0 首页正在拍卖 组合控件
 */
public class CustomTextOnPic extends RelativeLayout {

    private AutoHeightImageView customPicIv;
    private TextView customTitleTv;
    private TextView customTextTv;


    public CustomTextOnPic(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_pictext, this);

        customPicIv = (AutoHeightImageView) view.findViewById(R.id.custom_pic_iv);
        customTitleTv = (TextView) view.findViewById(R.id.custom_title_tv);
        customTextTv = (TextView) view.findViewById(R.id.custom_text_tv);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTextOnImg);
        if (typedArray != null) {

            // 为图片添加特性
            int picBackgroud = typedArray.getResourceId(R.styleable.CustomTextOnImg_pic_background, 0);
            float picWidth = typedArray.getDimension(R.styleable.CustomTextOnImg_pic_background_width, 25);
            float picHeight = typedArray.getDimension(R.styleable.CustomTextOnImg_pic_background_height, 25);
            boolean isRound = typedArray.getBoolean(R.styleable.CustomTextOnImg_pic_is_round, false);
            if (picBackgroud != 0) {
                if (isRound) {
                    ImageLodeUtils.loadDrawable(getContext(), picBackgroud, customPicIv, true);
                } else {
                    customPicIv.setImageResource(picBackgroud);
                }
            }

            // 为TEXT设置属性
            String picText = typedArray.getString(R.styleable.CustomTextOnImg_pic_text);
            int picTextColor = typedArray.getColor(R.styleable.CustomTextOnImg_pic_text_color,
                    ContextCompat.getColor(context, R.color.price_color));
            int picTextSize = typedArray.getDimensionPixelOffset(
                    R.styleable.CustomTextOnImg_pic_text_size, 10);
            if (!TextUtils.isEmpty(picText)) {
                customTextTv.setText(picText);
            }
//            customTextTv.setTextColor(picTextColor);
//            customTextTv.setTextSize(picTextSize);

            // 为TITLE设置属性
            String picTitle = typedArray.getString(R.styleable.CustomTextOnImg_pic_title);
            int picTitleColor = typedArray.getColor(R.styleable.CustomTextOnImg_pic_title_color,
                    ContextCompat.getColor(context, R.color.color_63));
            int picTitleSize = typedArray.getDimensionPixelOffset(
                    R.styleable.CustomTextOnImg_pic_title_size, 12);

            if (!TextUtils.isEmpty(picTitle)) {
                customTitleTv.setText(picTitle);
            }
//            customTitleTv.setTextColor(picTitleColor);
//            customTitleTv.setTextSize(picTitleSize);

            typedArray.recycle();
        }

    }

    public void setImageUrl(Activity mContext, String imageUrl) {
        ImageLodeUtils.loadingImage(mContext, imageUrl, customPicIv);
    }

    public void setRoundImageUrl(Activity mContext, String imageUrl) {
        ImageLodeUtils.loadingRoundImage(mContext, imageUrl, customPicIv);
    }

    public void setTitleText(String textStr) {
        customTitleTv.setText(textStr);
    }

    public void setTitleColor(@ColorInt int color) {
        customTitleTv.setTextColor(color);
    }

    public void setTipText(String textStr) {
        customTextTv.setText(textStr);
    }

    public void setTipColor(@ColorInt int color) {
        customTextTv.setTextColor(color);
    }
}
