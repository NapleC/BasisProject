package com.dxs.lib_mvpbase_appcompat.view;

import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 *  created by hl at 2018/5/7
 *  PagerView
 */
public interface PagerView extends BaseView {

    ViewPager getViewPager();

    List<View> getPager();

    TabLayout getTablayout();

    String[] getTabString();

    @DrawableRes
    int[] getTabDrawables();

    @LayoutRes
    int getTabLayoutItem();

    /**
     * 是否需要切换动画
     *
     * @return
     */
    boolean isAnimation();
}
