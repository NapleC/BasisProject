package com.dxs.lib_mvpbase_appcompat.templet.helper;

import android.view.View;

import com.dxs.lib_mvpbase_appcompat.templet.options.ToolbarOptions;
import com.dxs.lib_mvpbase_appcompat.view.ToolbarView;

/**
 *  created by hl at 2018/5/7
 *  MDToolBarHelperImpl
 */
public class MDToolBarHelperImpl extends BaseToolBarHelperImpl {

    public MDToolBarHelperImpl(ToolbarView uiView, View rootView, int toolbarLayout) {
        super(uiView, rootView, toolbarLayout);
    }

    @Override
    public void initToolbar() {
        ToolbarHelper.SimpleInitToolbar(mToolbarView.getContext(), mToolbar, true);
    }

    @Override
    public void setToolbarOptions(ToolbarOptions options) {
        super.setToolbarOptions(options);

    }

    @Override
    public void setTextSize(int size) {

    }

    @Override
    public void setTitleSize(int size) {

    }
}
