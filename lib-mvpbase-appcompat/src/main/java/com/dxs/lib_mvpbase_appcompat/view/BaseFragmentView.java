package com.dxs.lib_mvpbase_appcompat.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 *  created by hl at 2018/5/7
 *  BaseFragmentView
 */
public interface BaseFragmentView extends UIView {
    Bundle getBundle();
    Fragment getFragment();
}
