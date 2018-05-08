package com.dxs.lib_mvpbase_appcompat.model;

import android.support.annotation.DrawableRes;

/**
 *  created by hl at 2018/5/7
 *  PagerModel
 */

public interface PagerModel {

    Class[] getFragments();

    String[] getTabString();

    @DrawableRes
    int[] getTabDrawables();

    boolean isAnimation();
}
