package com.dxs.lib_mvpbase_appcompat.application;

import android.app.Application;

/**
 *  created by hl at 2018/5/7
 *  BaseApplication
 */
public class BaseApplication extends Application {
    private static Application mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Application getContext() {
        return mContext;
    }
}
