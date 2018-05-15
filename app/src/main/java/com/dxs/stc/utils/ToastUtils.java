package com.dxs.stc.utils;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;


/**
 *  created by hl at 2018/5/9
 *  ToastUtils
 *  /**
 * 特点：
 * 1. 可以自定义 view（内部必须包含一个 textview，且 id 为： android:id="@android:id/message"）
 * 2. 自定义显示位置 （设置对齐方式、偏移量）
 * 3. 线程安全，线程内转到主线程中运行
 * 4. 不需传入 context。反射获取 application, 支持自定义 application。
 * 5. 支持取消显示
 * 6. 支持避免多次弹出 toast，后面的覆盖前面的
 * <p>
 * 该工具类使用到了 ThreadUtils（切换线程）,AppUtils（获取 application）
 * </p>
 *
 *
 */
public class ToastUtils {

    //-- 参数 --

    // 使用的 toast
    private static Toast sToast;
    // 默认的位置
    private static int gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    //x,y 偏移量
    private static int xOffset = 0;
    private static int yOffset = 0;
    // 自定义 view
    private static View customView;


    private ToastUtils() {
        throw new AssertionError();
    }

    /**
     * 设置 toast 位置
     *
     * @param gravity 位置
     * @param xOffset x 偏移
     * @param yOffset y 偏移
     */
    public static void setGravity(int gravity, int xOffset, int yOffset) {
        ToastUtils.gravity = gravity;
        ToastUtils.xOffset = xOffset;
        ToastUtils.yOffset = yOffset;
    }

    /**
     * 设置 toast view
     *
     * @param layoutId 视图
     */
    public static void setView(@LayoutRes int layoutId) {
        ToastUtils.customView = View.inflate(AppUtils.INSTANCE, layoutId, null);
    }

    /**
     * 设置 toast view
     *
     * @param view 视图
     */
    public static void setView(View view) {
        ToastUtils.customView = view;
    }

    /**
     * 获取 toast view
     *
     * @return view 自定义 view
     */
    public static View getView() {
        if (customView != null) return customView;
        if (sToast != null) return sToast.getView();
        return null;
    }

    /**
     * 安全地显示短时 toast
     *
     * @param text 文本
     */
    public static void showShortSafe(final CharSequence text) {
        ThreadUtils.runInUiThread(new Runnable() {
            @Override
            public void run() {
                show(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时 toast
     *
     * @param resId 资源 Id
     */
    public static void showShortSafe(final @StringRes int resId) {
        ThreadUtils.runInUiThread(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时 toast
     *
     * @param resId 资源 Id
     * @param args  参数
     */
    public static void showShortSafe(final @StringRes int resId, final Object... args) {
        ThreadUtils.runInUiThread(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_SHORT, args);

            }
        });
    }

    /**
     * 安全地显示短时 toast
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShortSafe(final String format, final Object... args) {
        ThreadUtils.runInUiThread(new Runnable() {
            @Override
            public void run() {
                show(format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示长时 toast
     *
     * @param text 文本
     */
    public static void showLongSafe(final CharSequence text) {
        ThreadUtils.runInUiThread(new Runnable() {
            @Override
            public void run() {
                show(text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时 toast
     *
     * @param resId 资源 Id
     */
    public static void showLongSafe(final @StringRes int resId) {
        ThreadUtils.runInUiThread(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时 toast
     *
     * @param resId 资源 Id
     * @param args  参数
     */
    public static void showLongSafe(final @StringRes int resId, final Object... args) {
        ThreadUtils.runInUiThread(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 安全地显示长时 toast
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLongSafe(final String format, final Object... args) {

        ThreadUtils.runInUiThread(new Runnable() {
            @Override
            public void run() {
                show(format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 显示短时 toast
     *
     * @param text 文本
     */
    public static void showShort(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时 toast
     *
     * @param resId 资源 Id
     */
    public static void showShort(@StringRes int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时 toast
     *
     * @param resId 资源 Id
     * @param args  参数
     */
    public static void showShort(@StringRes int resId, Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示短时 toast
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShort(String format, Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时 toast
     *
     * @param text 文本
     */
    public static void showLong(CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时 toast
     *
     * @param resId 资源 Id
     */
    public static void showLong(@StringRes int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时 toast
     *
     * @param resId 资源 Id
     * @param args  参数
     */
    public static void showLong(@StringRes int resId, Object... args) {
        show(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时 toast
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLong(String format, Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示 toast
     *
     * @param resId    资源 Id
     * @param duration 显示时长
     */
    private static void show(@StringRes int resId, int duration) {
        show(AppUtils.INSTANCE.getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示 toast
     *
     * @param resId    资源 Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void show(@StringRes int resId, int duration, Object... args) {
        show(String.format(AppUtils.INSTANCE.getResources().getString(resId), args), duration);
    }

    /**
     * 显示 toast
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }

    /**
     * 显示 toast
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void show(CharSequence text, int duration) {
        cancel();
        if (customView != null) {
            sToast = new Toast(AppUtils.INSTANCE);
            sToast.setView(customView);
            sToast.setDuration(duration);
            sToast.setText(text);
            sToast.setGravity(gravity, xOffset, yOffset);
        } else {
            sToast = Toast.makeText(AppUtils.INSTANCE, text, duration);
        }

        sToast.show();
    }


    /**
     * 取消 toast 显示
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
