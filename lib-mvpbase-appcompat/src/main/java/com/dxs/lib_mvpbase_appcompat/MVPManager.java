package com.dxs.lib_mvpbase_appcompat;

import com.dxs.lib_mvpbase_appcompat.templet.options.ToolbarOptions;

/**
 *  created by hl at 2018/5/7
 *  MVPManager
 */
public class MVPManager {
    public static ToolbarOptions mToolbarOptions;

    public static ToolbarOptions getToolbarOptions() {
        if (mToolbarOptions == null) {
            return ToolbarOptions.Create();
        }
        return mToolbarOptions;
    }

    public static void setToolbarOptions(ToolbarOptions toolbarOptions) {
        mToolbarOptions = toolbarOptions;
    }

}
