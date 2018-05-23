package com.dxs.stc.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ScrollView;

/**
 * created by hl at 2018/5/23
 * com.dxs.stc.widget.DropZoomScrollView
 *
 * @version V1.0 拉伸的弹簧效果
 * @copyright https://blog.csdn.net/qq_20451879/article/details/79289955
 * 注意使用的时候需要放大的 view, 一般是第一个。
 * RelativeLayout 或者 LinearLayout 要加上  android:gravity="center_horizontal"
 */

public class DropZoomScrollView extends ScrollView implements View.OnTouchListener {
    // 记录首次按下位置
    private float mFirstPosition = 0;
    // 是否正在放大
    private Boolean mScaling = false;

    private View dropZoomView;
    private int dropZoomViewWidth;
    private int dropZoomViewHeight;

    public DropZoomScrollView(Context context) {
        super(context);
    }

    public DropZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DropZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void init() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        if (getChildAt(0) != null) {
            ViewGroup vg = (ViewGroup) getChildAt(0);
            if (vg.getChildAt(0) != null) {
                dropZoomView = vg.getChildAt(0);
                setOnTouchListener(this);

            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (dropZoomViewWidth <= 0 || dropZoomViewHeight <= 0) {
            dropZoomViewWidth = dropZoomView.getMeasuredWidth();
            dropZoomViewHeight = dropZoomView.getMeasuredHeight();
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //手指离开后恢复图片
                mScaling = false;
                replyImage();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScaling) {
                    if (getScrollY() == 0) {
                        mFirstPosition = event.getY();// 滚动到顶部时记录位置，否则正常返回
                    } else {
                        break;
                    }
                }
                int distance = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
                if (distance < 0) { // 当前位置比记录位置要小，正常返回
                    break;
                }

                // 处理放大
                mScaling = true;
                setZoom(1 + distance);
                return true; // 返回true表示已经完成触摸事件，不再处理
        }
        return false;
    }

    // 回弹动画 (使用了属性动画)
    public void replyImage() {
        final float distance = dropZoomView.getMeasuredWidth() - dropZoomViewWidth;

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration((long) (distance * 0.7));

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                setZoom(distance - ((distance) * cVal));
            }
        });
        anim.start();

    }

    //缩放
    public void setZoom(float s) {
        if (dropZoomViewHeight <= 0 || dropZoomViewWidth <= 0) {
            return;
        }
        ViewGroup.LayoutParams lp = dropZoomView.getLayoutParams();
        lp.width = (int) (dropZoomViewWidth + s);
        lp.height = (int) (dropZoomViewHeight * ((dropZoomViewWidth + s) / dropZoomViewWidth));
        dropZoomView.setLayoutParams(lp);
    }

    public class MyAnimation extends Animation {

        private ImageView imageView;
        // imageview 的原始高度
        private int targetHeight;
        // 当前 imageview 的高度
        private int currentHeight;
        // 高度差 当前的减去原始的
        private int extraHeight;

        public MyAnimation(ImageView imageView, int targetHeight){
            this.imageView = imageView;
            this.targetHeight = targetHeight;
            this.currentHeight = imageView.getHeight();
            this.extraHeight = this.currentHeight - this.targetHeight;
        }

        /**
         *  当动画在不断的执行的时候回调该方法（就是监听动画执行的过程）
         * @param interpolatedTime 值得范围 0.0 到 1.0，时间变化因子
         * @param t
         */
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            this.imageView.getLayoutParams().height = (int)(this.currentHeight
                    - extraHeight * interpolatedTime);

            this.imageView.requestLayout();
        }
    }
}
