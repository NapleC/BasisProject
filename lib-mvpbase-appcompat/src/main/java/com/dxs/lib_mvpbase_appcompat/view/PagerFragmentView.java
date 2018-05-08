package com.dxs.lib_mvpbase_appcompat.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

/**
 *  created by hl at 2018/5/7
 *  PagerFragmentView
 */
public interface PagerFragmentView extends PagerView {

    ViewPager getViewPager();

    FragmentManager getFgManager();

    Class<Fragment>[] getFragments();

}
