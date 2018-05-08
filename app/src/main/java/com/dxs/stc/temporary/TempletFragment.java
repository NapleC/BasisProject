package com.dxs.stc.temporary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dxs.lib_mvpbase_appcompat.MVPManager;
import com.dxs.lib_mvpbase_appcompat.R;
import com.dxs.lib_mvpbase_appcompat.base.BaseActivity;
import com.dxs.lib_mvpbase_appcompat.base.BaseFragment;
import com.dxs.lib_mvpbase_appcompat.templet.helper.ToolbarHelper;
import com.dxs.lib_mvpbase_appcompat.templet.options.ToolbarOptions;
import com.dxs.lib_mvpbase_appcompat.presenter.BasePresenter;
import com.dxs.lib_mvpbase_appcompat.templet.weight.LoadingPager;
import com.dxs.lib_mvpbase_appcompat.view.ToolbarView;

/**
 *  created by hl at 2018/5/7
 *  TempletFragment
 *  模拟fragment
 */
public abstract class TempletFragment<T extends BasePresenter> extends BaseFragment<T>
        implements ToolbarView {
    private ToolbarHelper mToolbarHelper;
    private View rootView;

    /**
     * @param inflater
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public View initView(@NonNull LayoutInflater inflater, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.templet_layout, null);
        //初始化一次
        mToolbarHelper = getToolbarHelper();
//        //ContentView容器
        LoadingPager loadingPager = (LoadingPager) rootView.findViewById(R.id.templet_content);
//        //真正的创建contentView
        View contentView = super.initView(inflater, savedInstanceState);
//        contentGroup.removeAllViews();
        loadingPager.setSuccessPage(contentView);
        return rootView;
    }

    /**
     * 默认使用base_toolbar
     * 如果不需要toolbar,请复写,并返回0.或者-1
     *
     * @return
     */
    @Override
    public int getToolbarLayout() {
        return ToolbarHelper.TOOLBAR_TEMPLET_DEFUATL;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * 每次菜单被关闭时调用.（菜单被关闭有三种情形，menu按钮被再次点击、back按钮被点击或者用户选择了某一个菜单项）
     */
    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    /**
     * 菜单项被点击时调用，也就是菜单项的监听方法。
     * 通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


//    /**
//     * 切换MaterialDesign风格.
//     *
//     * @param isMaterialDesign
//     */
//    @Override
//    public void setMaterialDesignEnabled(boolean isMaterialDesign) {
//        getToolbarHelper().setMaterialDesignEnabled(isMaterialDesign);
//    }

    @Override
    public boolean isMaterialDesign() {
        return false;
    }

    /**
     * 如果设置的主题不是NoActionBar或者initToolbar()返回是0,则返回null.
     *
     * @return mToolbar 可能为null.
     */
    public Toolbar getToolbar() {
        return getToolbarHelper().getToolbar();
    }

    /**
     * @return
     */
    @Override
    public ToolbarHelper getToolbarHelper() {
        if (mToolbarHelper == null) {
            mToolbarHelper = ToolbarHelper.Create(this, rootView);
        }
        return mToolbarHelper;
    }

    @Override
    protected abstract T initPresenter();
}
