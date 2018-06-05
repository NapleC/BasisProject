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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.utils.DensityUtils;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

/**
 * created by hl at 2018/6/4
 * CustomSortArrow
 */
public class CustomSortArrow extends RelativeLayout {

    private TextView customTitleTv;
    private ImageView customRiseIv;
    private ImageView customDropIv;


    public CustomSortArrow(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_sort_selector, this);

        customTitleTv = (TextView) view.findViewById(R.id.tv_sort_title);
        customRiseIv = (ImageView) view.findViewById(R.id.iv_sort_rise);
        customDropIv = (ImageView) view.findViewById(R.id.iv_sort_drop);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomArrowSort);
        if (typedArray != null) {

            boolean titleSelected = typedArray.getBoolean(R.styleable.CustomArrowSort_sort_is_selected, false);
            boolean riseVisible = typedArray.getBoolean(R.styleable.CustomArrowSort_sort_rise_visible, true);
            boolean dropVisible = typedArray.getBoolean(R.styleable.CustomArrowSort_sort_drop_visible, true);
            boolean isRise = typedArray.getBoolean(R.styleable.CustomArrowSort_sort_is_rise, false);
            boolean isDrop = typedArray.getBoolean(R.styleable.CustomArrowSort_sort_is_drop, false);

            // 为TITLE设置属性
            String sortTitle = typedArray.getString(R.styleable.CustomArrowSort_sort_title);
            int sortTitleColor = typedArray.getColor(R.styleable.CustomArrowSort_sort_title_color,
                    ContextCompat.getColor(context, R.color.navColor));
            int sortTitleSize = typedArray.getDimensionPixelOffset(
                    R.styleable.CustomArrowSort_sort_title_size, 14);
            if (!TextUtils.isEmpty(sortTitle)) {
                customTitleTv.setText(sortTitle);
            }

            customTitleTv.setSelected(titleSelected);
            customRiseIv.setSelected(isRise);
            customDropIv.setSelected(isDrop);
            customRiseIv.setVisibility(riseVisible ? VISIBLE : GONE);
            customDropIv.setVisibility(dropVisible ? VISIBLE : GONE);

            typedArray.recycle();
        }

    }

    public void setRiseVisible(boolean isVisible) {
        customRiseIv.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void setDropVisible(boolean isVisible) {
        customDropIv.setVisibility(isVisible ? VISIBLE : GONE);
    }

    public void setSortIsRise(boolean isSelected) {
        customRiseIv.setSelected(isSelected);
        customDropIv.setSelected(!isSelected);
    }

    public void setTitleSelected(boolean isSelected) {
        customTitleTv.setSelected(isSelected);
    }

    public void setTitleText(String textStr) {
        customTitleTv.setText(textStr);
    }

    public void setTitleColor(@ColorInt int color) {
        customTitleTv.setTextColor(color);
    }

    public void setTitleSize(float size) {
        customTitleTv.setTextSize(size);
    }
}
