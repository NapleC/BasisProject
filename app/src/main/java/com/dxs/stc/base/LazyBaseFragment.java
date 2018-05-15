package com.dxs.stc.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 * 若把初始化内容放到 initData 实现
 * 就是采用 Lazy 方式加载的 Fragment
 * 若不需要 Lazy 加载则 initData 方法内留空, 初始化内容放到 initViews 即可
 *
 * 注 1:
 * 如果是与 ViewPager 一起使用，调用的是 setUserVisibleHint。
 *
 * 注 2:
 * 如果是通过 FragmentTransaction 的 show 和 hide 的方法来控制显示，调用的是 onHiddenChanged.
 * 针对初始就 show 的 Fragment 为了触发 onHiddenChanged 事件 达到 lazy 效果 需要先 hide 再 show
 * eg:
 * transaction.hide(aFragment);
 * transaction.show(aFragment);
 *
 * update 2017/01/23
 * 忽略 isFirstLoad 的值，强制刷新数据，但仍要 Visible & Prepared
 * 一般用于 PagerAdapter 需要同时刷新全部子 Fragment 的场景
 * 不要 new 新的 PagerAdapter 而采取 reset 数据的方式
 * 所以要求 Fragment 重新走 initData 方法
 * 故使用 {@link LazyBaseFragment#setForceLoad(boolean)} 来让 Fragment 下次执行 initData
 *
 * Created by Mumu
 * on 2015/11/2.
 * </pre>
 */
public abstract class LazyBaseFragment extends Fragment {

    /**
     * 是否可见状态 为了避免和 {@link Fragment#isVisible()} 冲突 换个名字
     */
    private boolean isFragmentVisible;
    /**
     * 标志位，View 已经初始化完成。
     * 2016/04/29
     * 用 isAdded() 属性代替
     * 2016/05/03
     * isPrepared 还是准一些, isAdded 有可能出现 onCreateView 没走完但是 isAdded 了
     */
    private boolean isPrepared;
    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;
    /**
     * <pre>
     * 忽略 isFirstLoad 的值，强制刷新数据，但仍要 Visible & Prepared
     * 一般用于 PagerAdapter 需要刷新各个子 Fragment 的场景
     * 不要 new 新的 PagerAdapter 而采取 reset 数据的方式
     * 所以要求 Fragment 重新走 initData 方法
     * 故使用 {@link LazyBaseFragment#setForceLoad(boolean)} 来让 Fragment 下次执行 initData
     * </pre>
     */
    private boolean forceLoad = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.size() > 0) {
            initVariables(bundle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 若 viewpager 不设置 setOffscreenPageLimit 或设置数量不够
        // 销毁的 Fragment onCreateView 每次都会执行 (但实体类没有从内存销毁)
        // 导致 initData 反复执行, 所以这里注释掉
        // isFirstLoad = true;

        // 2016/04/29
        // 取消 isFirstLoad = true 的注释 , 因为上述的 initData 本身就是应该执行的
        // onCreateView 执行 证明被移出过 FragmentManager initData 确实要执行.
        // 如果这里有数据累加的 Bug 请在 initViews 方法里初始化您的数据 比如 list.clear();
        isFirstLoad = true;
        View view = initViews(inflater, container, savedInstanceState);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    /**
     * 如果是与 ViewPager 一起使用，调用的是 setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    /**
     * 如果是通过 FragmentTransaction 的 show 和 hide 的方法来控制显示，调用的是 onHiddenChanged.
     * 若是初始就 show 的 Fragment 为了触发该事件 需要先 hide 再 show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     * visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInvisible();
        }
    }

    protected void onVisible() {
        isFragmentVisible = true;
        lazyLoad();
    }

    protected void onInvisible() {
        isFragmentVisible = false;
    }

    /**
     * 要实现延迟加载 Fragment 内容, 需要在 onCreateView
     * isPrepared = true;
     */
    protected void lazyLoad() {
        if (isPrepared() && isFragmentVisible()) {
            if (forceLoad || isFirstLoad()) {
                forceLoad = false;
                isFirstLoad = false;
                initData();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
    }

    /**
     * 被 ViewPager 移出的 Fragment 下次显示时会从 getArguments() 中重新获取数据
     * 所以若需要刷新被移除 Fragment 内的数据需要重新 put 数据 eg:
     * Bundle args = getArguments();
     * if (args != null) {
     * args.putParcelable(KEY, info);
     * }
     */
    public void initVariables(Bundle bundle) {}

    protected abstract View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initData();

    public boolean isPrepared() {
        return isPrepared;
    }

    /**
     * 忽略 isFirstLoad 的值，强制刷新数据，但仍要 Visible & Prepared
     */
    public void setForceLoad(boolean forceLoad) {
        this.forceLoad = forceLoad;
    }

    public boolean isFirstLoad() {
        return isFirstLoad;
    }

    public boolean isFragmentVisible() {
        return isFragmentVisible;
    }

}
