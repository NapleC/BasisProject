package com.dxs.stc.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.util.AttributeSet;

import com.dxs.stc.R;
import com.dxs.stc.utils.Loger;

/**
 * created by hl at 2018/6/14
 * com.dxs.stc.widget.UnderLineTextView
 *
 * @version V1.0 <描述当前版本功能>
 */
public class UnderLineTextView extends AppCompatTextView {

    private Rect mRect;
    private Paint mPaint;
    private int mColor;
    private float density;
    private float mStrokeWidth = 8;

    public UnderLineTextView(Context context) {
        this(context, null, 0);
    }

    public UnderLineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //获取屏幕密度
        density = context.getResources().getDisplayMetrics().density;
        //获取自定义属性
        @SuppressLint("CustomViewStyleable")
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.UnderlinedTextView, defStyleAttr, 0);
        mColor = array.getColor(R.styleable.UnderlinedTextView_underlineColor, 0xFFFF0000);

        mStrokeWidth = array.getDimensionPixelSize(R.styleable.UnderlinedTextView_underlineWidth, (int) (density * 2));
        Loger.debug("density * 2: " + density * 2);
        Loger.debug("Stroke width: " + mStrokeWidth);
        array.recycle();

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mStrokeWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //得到TextView显示有多少行
        int count = getLineCount();

        //得到TextView的布局
        final Layout layout = getLayout();

        float x_start, x_stop, x_diff;
        int firstCharInLine, lastCharInLine;

        for (int i = 0; i < count; i++) {
            int baseline = getLineBounds(i, mRect) + 8;
            firstCharInLine = layout.getLineStart(i);
            lastCharInLine = layout.getLineEnd(i);
            x_start = layout.getPrimaryHorizontal(firstCharInLine);
            x_diff = layout.getPrimaryHorizontal(firstCharInLine + 1) - x_start;
            x_stop = layout.getPrimaryHorizontal(lastCharInLine - 1) + x_diff;

            canvas.drawLine(x_start, baseline + mStrokeWidth, x_stop, baseline + mStrokeWidth, mPaint);


        }
        super.onDraw(canvas);

    }

    public int getUnderLineColor() {
        return mColor;
    }

    public void setUnderLineColor(int mColor) {
        this.mColor = mColor;
        invalidate();
    }

    public float getUnderlineWidth() {
        return mStrokeWidth;
    }

    public void setUnderlineWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        invalidate();
    }
}
