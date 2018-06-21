package com.dxs.stc.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * created by hl at 2018/6/19
 * com.dxs.stc.widget.SquareViewPager
 *
 * @version V1.0 <描述当前版本功能>
 */
public class SquareViewPager extends ViewPager {

    public SquareViewPager(@NonNull Context context) {
        super(context);
    }

    public SquareViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = this.getMeasuredWidth();
        setMeasuredDimension(w + getPaddingLeft() + getPaddingRight(),
                w + getPaddingTop() + getPaddingBottom());
    }
}
