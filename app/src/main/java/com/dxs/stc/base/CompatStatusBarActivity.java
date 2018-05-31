package com.dxs.stc.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dxs.stc.R;
import com.dxs.stc.activities.MainActivity;
import com.dxs.stc.utils.OsUtil;

/**
 *  created by hl at 2018/5/8
 *  CompatStatusBarActivity
 */

public class CompatStatusBarActivity extends StatusBarBaseActivity {

    private FrameLayout mFrameLayoutContent;
    private View mViewStatusBarPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_compat_status_bar);

        mViewStatusBarPlace = findViewById(R.id.view_status_bar_place);
        mFrameLayoutContent = (FrameLayout) findViewById(R.id.frame_layout_content_place);

        ViewGroup.LayoutParams params = mViewStatusBarPlace.getLayoutParams();
        params.height = getStatusBarHeight();
        mViewStatusBarPlace.setLayoutParams(params);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //这里能够看到 我们这里其实是一个适配基类。布局中增加了一个View 用来适配状态栏的高度并调整颜色
        //contentLayout会将继承自这个Activity的页面的layout添加进去以达到通用的目的
        View contentView = LayoutInflater.from(this).inflate(layoutResID, null);
        mFrameLayoutContent.addView(contentView);
    }

    /**
     * 根据版本不同 修改添加View的颜色
     * 适配白底标题栏(方案二)顶部添加View,改变View颜色
     * 适配方案2, 4.4以下的不适配，4.4-6.0修改View颜色为浅灰色，6.0以上修改View颜色为白色，修改状态栏字体颜色
     *
     * @param isDark 标题栏颜色是否为深色(非背景色)
     */
    protected void setViewColorStatusBar(boolean isDark, int statusBarPlaceColor) {
        //6.0+ 小米 魅族 可以直接适配 一般情况下6.0以上都是透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || OsUtil.isMIUI() || OsUtil.isFlyme()) {
            setStatusBarTextDark(isDark);
            setStatusBarPlaceColor(statusBarPlaceColor);
        } else {
            if (statusBarPlaceColor == Color.WHITE) {
                statusBarPlaceColor = 0xffcccccc;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //4.4以上修改为浅灰色
                setStatusBarPlaceColor(statusBarPlaceColor);
            } else { //4.4以下不适配
                setStatusBarPlaceVisible(false);
            }
        }
    }

    protected void setStatusBarPlaceVisible(boolean isVisible) {
        if (isVisible) {
            mViewStatusBarPlace.setVisibility(View.VISIBLE);
        } else {
            mViewStatusBarPlace.setVisibility(View.GONE);
        }
    }

    protected void setStatusBarPlaceColor(int statusColor) {
        if (mViewStatusBarPlace != null) {
            mViewStatusBarPlace.setBackgroundColor(statusColor);
        }
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (intent == null) return;
        if (intent.getComponent() == null) return;
        String className = intent.getComponent().getClassName();
        if (!className.equals(MainActivity.class.getName())) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (intent == null) return;
        if (intent.getComponent() == null) return;
        String className = intent.getComponent().getClassName();
        if (!className.equals(MainActivity.class.getName())) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (!((Object) this).getClass().equals(MainActivity.class)) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}
