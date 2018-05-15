package com.dxs.stc.base;

import android.support.annotation.StringRes;

/**
 *  created by hl at 2018/5/9
 *  BaseView
 */
public interface BaseView {
    void showTipMsg(String msg);

    void showTipMsg(@StringRes int msg);

    void showLoading();

    void hideLoading();

    void invalidToken();

    void myFinish();
}
