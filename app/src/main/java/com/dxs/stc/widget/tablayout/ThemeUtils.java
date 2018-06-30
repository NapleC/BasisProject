package com.dxs.stc.widget.tablayout;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * created by hl at 2018/6/30
 * com.dxs.stc.widget.tablayout.ThemeUtils
 *
 * @version V1.0 <描述当前版本功能>
 */
public class ThemeUtils {

    private static final int[] APPCOMPAT_CHECK_ATTRS = {
            android.support.v7.appcompat.R.attr.colorPrimary
    };

    static void checkAppCompatTheme(Context context) {
        TypedArray a = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
        final boolean failed = !a.hasValue(0);
        a.recycle();
        if (failed) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme "
                    + "(or descendant) with the design library.");
        }
    }
}
