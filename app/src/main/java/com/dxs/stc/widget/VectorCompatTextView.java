package com.dxs.stc.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import com.dxs.stc.R;

/**
 * created by hl at 2018/6/29
 * com.dxs.stc.widget.VectorCompatTextView
 *
 * @version V1.0
 *  1.适配 VectorDrawable 矢量图（svg）
 *  2.灵活地设置 CompoundDrawable 的尺寸大小
 *  3.为 CompoundDrawable 着色
 *  4.支持 StateListDrawable（主要是 checked_state）
 */
public class VectorCompatTextView extends AppCompatCheckedTextView {

    private boolean isTintVectorInTextColor;
    private int mVectorCompatColor;
    private boolean isVectorAdjustTextWidth;
    private boolean isVectorAdjustTextHeight;
    private boolean isVectorAdjustViewWidth;
    private boolean isVectorAdjustViewHeight;
    private int mVectorWidth;
    private int mVectorHeight;

    public VectorCompatTextView(Context context) {
        this(context, null);
    }

    public VectorCompatTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VectorCompatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VectorCompatTextView);

            Drawable dl = null;
            Drawable dt = null;
            Drawable dr = null;
            Drawable db = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dl = a.getDrawable(R.styleable.VectorCompatTextView_vectorLeftCompat);
                dt = a.getDrawable(R.styleable.VectorCompatTextView_vectorTopCompat);
                dr = a.getDrawable(R.styleable.VectorCompatTextView_vectorRightCompat);
                db = a.getDrawable(R.styleable.VectorCompatTextView_vectorBottomCompat);
            } else {
                int dlId = a.getResourceId(R.styleable.VectorCompatTextView_vectorLeftCompat, -1);
                int dtId = a.getResourceId(R.styleable.VectorCompatTextView_vectorTopCompat, -1);
                int drId = a.getResourceId(R.styleable.VectorCompatTextView_vectorRightCompat, -1);
                int dbId = a.getResourceId(R.styleable.VectorCompatTextView_vectorBottomCompat, -1);

                if (dlId != -1)
                    dl = AppCompatResources.getDrawable(context, dlId);
                if (dtId != -1)
                    dt = AppCompatResources.getDrawable(context, dtId);
                if (drId != -1)
                    dr = AppCompatResources.getDrawable(context, drId);
                if (dbId != -1)
                    db = AppCompatResources.getDrawable(context, dbId);
            }

            isTintVectorInTextColor = a.getBoolean(R.styleable.VectorCompatTextView_tintVectorInTextColor, false);
            mVectorCompatColor = a.getColor(R.styleable.VectorCompatTextView_vectorCompatColor, 0);
            isVectorAdjustTextWidth = a.getBoolean(R.styleable.VectorCompatTextView_vectorAdjustTextWidth, false);
            isVectorAdjustTextHeight = a.getBoolean(R.styleable.VectorCompatTextView_vectorAdjustTextHeight, false);
            isVectorAdjustViewWidth = a.getBoolean(R.styleable.VectorCompatTextView_vectorAdjustViewWidth, false);
            isVectorAdjustViewHeight = a.getBoolean(R.styleable.VectorCompatTextView_vectorAdjustViewHeight, false);
            mVectorWidth = a.getDimensionPixelSize(R.styleable.VectorCompatTextView_vectorWidth, 0);
            mVectorHeight = a.getDimensionPixelSize(R.styleable.VectorCompatTextView_vectorHeight, 0);
            a.recycle();

            if (mVectorWidth < 0)
                mVectorWidth = 0;
            if (mVectorHeight < 0)
                mVectorHeight = 0;
            if (isVectorAdjustTextWidth)
                isVectorAdjustViewWidth = false;
            if (isVectorAdjustTextHeight)
                isVectorAdjustViewHeight = false;

            initDrawables(dl, dt, dr, db);
        }
    }

    private void initDrawables(final Drawable... drawables) {
        for (Drawable drawable : drawables) {
            tintDrawable(drawable);
        }

        if (!isVectorAdjustTextWidth && !isVectorAdjustTextHeight && !isVectorAdjustViewWidth &&
                !isVectorAdjustViewHeight && mVectorWidth == 0 && mVectorHeight == 0) {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
        } else {
            if (isVectorAdjustTextWidth || isVectorAdjustTextHeight || isVectorAdjustViewWidth ||
                    isVectorAdjustViewHeight) {
                boolean invalid = (
                        (isVectorAdjustTextWidth || isVectorAdjustViewWidth) &&
                                (drawables[0] != null || drawables[2] != null))
                        ||
                        ((isVectorAdjustTextHeight || isVectorAdjustViewHeight)
                                && (drawables[1] != null || drawables[3] != null));
                if (invalid) {
                    if (mVectorWidth > 0 || mVectorHeight > 0) {
                        resizeDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
                    } else {
                        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
                    }
                } else {
                    getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @SuppressLint("ObsoleteSdkInt")
                        @Override
                        public void onGlobalLayout() {
                            if (Build.VERSION.SDK_INT < 16) {
                                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            } else {
                                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }

                            adjustDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
                        }
                    });
                }
            } else if (mVectorWidth > 0 || mVectorHeight > 0) {
                resizeDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
            }
        }
    }

    private void tintDrawable(Drawable drawable) {
        if (drawable != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                drawable = DrawableCompat.wrap(drawable).mutate();
            }
            if (isTintVectorInTextColor) {
                DrawableCompat.setTint(drawable, getCurrentTextColor());
            } else if (mVectorCompatColor != 0) {
                DrawableCompat.setTint(drawable, mVectorCompatColor);
            }
        }
    }

    private void resizeDrawables(Drawable... drawables) {
        for (Drawable drawable : drawables) {
            if (drawable == null) {
                continue;
            }

            if (mVectorWidth > 0 && mVectorHeight > 0) {
                drawable.setBounds(0, 0, mVectorWidth, mVectorHeight);
            } else if (mVectorWidth > 0) {
                int h = mVectorWidth * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
                drawable.setBounds(0, 0, mVectorWidth, h);
            } else {
                int w = mVectorHeight * drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
                drawable.setBounds(0, 0, w, mVectorHeight);
            }
        }

        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);

        if (isVectorAdjustTextWidth || isVectorAdjustTextHeight) {
            Drawable[] drawables = getCompoundDrawables();
            if (drawables[0] == null && drawables[1] == null && drawables[2] == null && drawables[3] == null)
                return;

            adjustDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        }
    }

    private void adjustDrawables(Drawable dl, Drawable dt, Drawable dr, Drawable db) {
        int width = 0;
        int height = 0;

        if (isVectorAdjustTextWidth) {
            Paint paint = new Paint();
            paint.setTextSize(getTextSize());
            CharSequence text = getText();
            Rect rect = new Rect();
            paint.getTextBounds(text.toString(), 0, text.length(), rect);

            width = rect.width();
        } else if (isVectorAdjustViewWidth) {
            width = getMeasuredWidth();
        }
        if (isVectorAdjustTextHeight) {
            Paint paint = new Paint();
            paint.setTextSize(getTextSize());
            CharSequence text = getText();
            Rect rect = new Rect();
            paint.getTextBounds(text.toString(), 0, text.length(), rect);

            height = rect.height();
        } else if (isVectorAdjustViewHeight) {
            height = getMeasuredHeight();
        }

        int h = mVectorHeight;
        int w = mVectorWidth;

        if (dt != null) {
            if (h == 0) h = width * dt.getIntrinsicHeight() / dt.getIntrinsicWidth();
            dt.setBounds(0, 0, width, h);
        }
        if (db != null) {
            if (h == 0) h = width * db.getIntrinsicHeight() / db.getIntrinsicWidth();
            db.setBounds(0, 0, width, h);
        }

        if (dl != null) {
            if (w == 0) w = height * dl.getIntrinsicWidth() / dl.getIntrinsicHeight();
            dl.setBounds(0, 0, w, height);
        }
        if (dr != null) {
            if (w == 0) w = height * dr.getIntrinsicWidth() / dr.getIntrinsicHeight();
            dr.setBounds(0, 0, w, height);
        }

        setCompoundDrawables(dl, dt, dr, db);
    }

    @Override
    public void setTextColor(@ColorInt int color) {
        super.setTextColor(color);

        refreshCompoundDrawables();
    }

    private void refreshCompoundDrawables() {
        Drawable[] drawables = getCompoundDrawables();
        for (Drawable drawable : drawables) {
            tintDrawable(drawable);
        }

        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    public boolean isTintVectorInTextColor() {
        return isTintVectorInTextColor;
    }

    public void setTintDrawableInTextColor(boolean tintDrawableInTextColor) {
        if (isTintVectorInTextColor == tintDrawableInTextColor)
            return;

        isTintVectorInTextColor = tintDrawableInTextColor;
        refreshCompoundDrawables();
    }

    public int getDrawableCompatColor() {
        return mVectorCompatColor;
    }

    public void setDrawableCompatColor(@ColorInt int drawableCompatColor) {
        if (mVectorCompatColor == drawableCompatColor)
            return;

        mVectorCompatColor = drawableCompatColor;
        refreshCompoundDrawables();
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        Drawable[] drawables = getCompoundDrawables();
        initDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    @Override
    public void toggle() {
        super.toggle();

        Drawable[] drawables = getCompoundDrawables();
        initDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (isTintVectorInTextColor) {
            Drawable[] drawables = getCompoundDrawables();

            boolean needRefresh = false;
            for (Drawable drawable : drawables) {
                if (drawable != null) {
                    needRefresh = true;
                    break;
                }
            }

            if (needRefresh) {
                refreshCompoundDrawables();
            }
        }
    }
}
