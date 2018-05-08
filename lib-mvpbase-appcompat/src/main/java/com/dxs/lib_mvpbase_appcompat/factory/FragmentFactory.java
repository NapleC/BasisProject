package com.dxs.lib_mvpbase_appcompat.factory;

import android.support.v4.app.Fragment;

/**
 *  created by hl at 2018/5/7
 *  FragmentFactory
 */
public class FragmentFactory {
    public static Fragment getFragment(Class<Fragment> zClass) {
        try {
            return zClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
