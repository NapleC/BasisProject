package com.dxs.stc.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.dxs.stc.R;

/**
 * created by hl at 2018/5/15
 * com.dxs.stc.widget
 */
public class AutoHeightImageView extends AppCompatImageView {

    private Paint paint;
    private int widthWeight = 1;
    private int heightWeight = 1;
    private boolean isRound = false;
    private int roundPx = 16;

    public AutoHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoHeightImageView);
        widthWeight = a.getInteger(R.styleable.AutoHeightImageView_widthWeight, 1);
        heightWeight = a.getInteger(R.styleable.AutoHeightImageView_heightWeight, 1);
        isRound = a.getBoolean(R.styleable.AutoHeightImageView_isRound,false);
        roundPx =  a.getInteger(R.styleable.AutoHeightImageView_roundPx, 16);
        a.recycle();

        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (!isRound){
            super.onDraw(canvas);
        } else {
            Drawable drawable = getDrawable();
            if (null != drawable && isRound) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                Bitmap b = getRoundBitmap(bitmap, 20);
                final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
                final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());
                paint.reset();
                canvas.drawBitmap(b, rectSrc, rectDest, paint);

            } else {
                super.onDraw(canvas);
            }
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = this.getMeasuredWidth();
        int h = w * heightWeight / widthWeight;
        setMeasuredDimension(w + getPaddingLeft() + getPaddingRight(), h + getPaddingTop() + getPaddingBottom());
    }


    /**
     * 获取圆角矩形图片方法
     *
     * @param bitmap
     * @param roundPx
     * @return Bitmap
     * @author caizhiming
     */
    private Bitmap getRoundBitmap(Bitmap bitmap, int roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = bitmap.getWidth();

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
