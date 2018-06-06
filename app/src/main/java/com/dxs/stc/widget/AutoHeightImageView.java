package com.dxs.stc.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.dxs.stc.R;

/**
 * created by hl at 2018/5/15
 * com.dxs.stc.widget
 */
public class AutoHeightImageView extends AppCompatImageView {

    private int widthWeight = 1;
    private int heightWeight = 1;

    public AutoHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoHeightImageView);
        widthWeight = a.getInteger(R.styleable.AutoHeightImageView_widthWeight, 1);
        heightWeight = a.getInteger(R.styleable.AutoHeightImageView_heightWeight, 1);
        a.recycle();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = this.getMeasuredWidth();
        int h = w * heightWeight / widthWeight;
        setMeasuredDimension(w + getPaddingLeft() + getPaddingRight(),
                h + getPaddingTop() + getPaddingBottom());
    }

}
