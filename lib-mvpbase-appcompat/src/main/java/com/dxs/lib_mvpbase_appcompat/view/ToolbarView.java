package com.dxs.lib_mvpbase_appcompat.view;

import android.support.annotation.LayoutRes;

import com.dxs.lib_mvpbase_appcompat.templet.helper.ToolbarHelper;
import com.dxs.lib_mvpbase_appcompat.templet.options.ToolbarOptions;

/**
 *  created by hl at 2018/5/7
 *  ToolbarView
 */
public interface ToolbarView extends BaseView {
    /**
     * 获得ToolbarHelper,Presenter可以通过ToolbarHelper的来控制toolbar
     */
    ToolbarHelper getToolbarHelper();

    /**
     * 是否使用MaterialDesign风格
     *
     * @return
     */
    boolean isMaterialDesign();

    /**
     * 通过这个修改toolbar的样式layout,不需要可以传0或者-1;
     *
     * @return
     */
    @LayoutRes
    int getToolbarLayout();

//    /**
//     * MaterialDesign风格,普通风格之间转换
//     *
//     * @param isMaterialDesign
//     */
//    void setMaterialDesignEnabled(boolean isMaterialDesign);

    /**
     * 回退
     */
    void onBack();

    ToolbarOptions getToolbarOptions();
}
