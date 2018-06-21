package com.dxs.stc.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * created by hl at 2018/6/21
 * com.dxs.stc.widget.OneScrollView
 *
 * @version V1.0 配合scrollview使用
 */
public class OneScrollView extends ScrollView {

    private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;

    private ScrollViewListener scrollViewListener = null;

    public OneScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    // Return false if we're scrolling in the x direction
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceY) > Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }

    //重写滚动方法
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public interface ScrollViewListener {

        void onScrollChanged(OneScrollView scrollView, int x, int y, int oldx, int oldy);

    }
}
