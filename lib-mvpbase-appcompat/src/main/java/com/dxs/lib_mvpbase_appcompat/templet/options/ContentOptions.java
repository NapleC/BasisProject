package com.dxs.lib_mvpbase_appcompat.templet.options;

import android.content.Context;
import android.view.View;

import com.dxs.lib_mvpbase_appcompat.templet.weight.LoadingPager;

/**
 *  created by hl at 2018/5/7
 *  ContentOptions
 */
public class ContentOptions {
    private int emptyLayout;
    private int errorLayout;
    private int loadingLayout;
    private boolean isOpenLoading;

    public static ContentOptions create() {
        return new ContentOptions();
    }

    public View build(Context context) {
        LoadingPager loadingPager = new LoadingPager(context);
        loadingPager.setEmptyLayout(emptyLayout);
        loadingPager.setErrorLayout(errorLayout);
        loadingPager.setLoadingLayout(loadingLayout);
        return loadingPager;
    }
}

