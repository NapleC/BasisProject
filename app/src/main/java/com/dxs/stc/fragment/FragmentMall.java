package com.dxs.stc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dxs.stc.R;
import com.dxs.stc.activities.BannerActivity;
import com.dxs.stc.activities.MallListActivity;
import com.dxs.stc.activities.SearchActivity;
import com.dxs.stc.adpater.MallHeaderProductsAdapter;
import com.dxs.stc.adpater.MallHeaderTopicAdapter;
import com.dxs.stc.adpater.MallRecyclerViewAdapter;
import com.dxs.stc.base.Constant;
import com.dxs.stc.base.LazyBaseFragment;
import com.dxs.stc.mvp.bean.MallTopicBean;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.mvp.presenter.IGetBookPresenter;
import com.dxs.stc.mvp.presenter.impl.GetBookPresenterImpl;
import com.dxs.stc.mvp.view.IBookView;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.utils.http.ParseErrorMsgUtil;
import com.dxs.stc.widget.GlideImageLoad;
import com.dxs.stc.utils.decoration.SpacesItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * created by hl at 2018/5/8
 * FragmentMall
 */

public class FragmentMall extends LazyBaseFragment implements IBookView {

    @BindView(R.id.tv_bar_text)
    TextView mBarText;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private MallRecyclerViewAdapter mAdapter;
    List<Movie.SubjectsBean> mData;

    private IGetBookPresenter iGetBookPresenter;

    private View view;
    private Unbinder unbinder;

    private View mHeaderView;
    private Banner mTopBanner;

    private MallHeaderProductsAdapter mProductsAdapter;
    private RecyclerView mHeaderProductsRv;
    private MallHeaderTopicAdapter mTopicAdapter;
    private RecyclerView mHeaderTopicRv;
    List<Movie.SubjectsBean> mProductsData;
    List<MallTopicBean> mTopicData;

    private int thePageIndex = 0;

    String[] images = new String[]{
            "https://image2.wbiao.co/upload/default/201702/07/1486396886665233850.jpg",
            "https://image2.wbiao.co/upload/default/201702/07/1486396886895810368.jpg",
            "https://image2.wbiao.co/upload/article/201702/17/1487322373441497976.jpg",
            "https://image2.wbiao.co/upload/default/201702/15/1487138933042431801.jpg"};

    int[] topicImages = new int[]{R.mipmap.ic_mall_bracelet, R.mipmap.ic_mall_ornaments,
            R.mipmap.ic_mall_pendant, R.mipmap.ic_mall_ring, R.mipmap.ic_mall_badge,
            R.mipmap.ic_mall_diamond, R.mipmap.ic_mall_earring, R.drawable.svg_mall_more};

    public static FragmentMall newInstance() {
        return new FragmentMall();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mall, null);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, view);
        Loger.debug("Fragment initViews");
        return view;
    }

    @Override
    protected void initData() {
        initViews();
    }

    private void initViews() {
        mBarText.setText(getString(R.string.title_mall));
        mTopicData = new ArrayList<>();
        String[] topicTitle = getResources().getStringArray(R.array.mall_header_topic_title);
        for (int k = 0, lenK = topicImages.length; k < lenK; k++) {
            MallTopicBean mallTopicBean = new MallTopicBean("1245" + k, topicTitle[k], topicImages[k]);
            mTopicData.add(mallTopicBean);
            mallTopicBean = null;
        }

        //-------------------------------------------------------------------
        mData = new ArrayList<>();
        mAdapter = new MallRecyclerViewAdapter(R.layout.item_home_book, mData);
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header_mall, null);
        mAdapter.setHeaderView(mHeaderView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));//这里用线性宫格显示 类似于grid view

        SpacesItemDecoration decoration = new SpacesItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.dp_14),
                getResources().getDimensionPixelSize(R.dimen.dp_10), 1);
        mRecyclerView.addItemDecoration(decoration);

        mRecyclerView.setAdapter(mAdapter);

        iGetBookPresenter = new GetBookPresenterImpl(this);

        refreshLayout.autoRefresh();

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Loger.debug("adapter onItemClick");
            ToastUtils.showShortSafe("点击的是第：" + position);
        });

        refreshLayout.setOnRefreshListener(refreshlayout -> {
            thePageIndex = 0;
            Loger.debug("onRefresh the start:" + thePageIndex);
            iGetBookPresenter.getBook(10 * thePageIndex, 10);
        });

        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setEnableLoadMore(false);

        // 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法
        mAdapter.setPreLoadNumber(3);
        mAdapter.setOnLoadMoreListener(() -> {
            Loger.debug("onLoadMore the start:" + thePageIndex);
            iGetBookPresenter.getBook(10 * thePageIndex, 10);
        }, mRecyclerView);

        changeTopSearchStyle();
        initHeaderView();


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), BannerActivity.class));
            }
        });

    }

    @OnClick({R.id.iv_bar_right, R.id.iv_bar_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_right:
                ToastUtils.showShort("进入消息");
                break;
            case R.id.iv_bar_left:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }

    private void initHeaderView() {

        mTopBanner = mHeaderView.findViewById(R.id.mall_banner);
        mHeaderProductsRv = mHeaderView.findViewById(R.id.rv_header_products_mall);
        mHeaderTopicRv = mHeaderView.findViewById(R.id.rv_header_mall_topic);
        mTopBanner.setImages(Arrays.asList(images)).setImageLoader(new GlideImageLoad()).start();
        mProductsAdapter = new MallHeaderProductsAdapter(R.layout.item_mall_header_products, mProductsData);
        mTopicAdapter = new MallHeaderTopicAdapter(R.layout.item_mall_header_topic, mTopicData);

//        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
//        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 RecyclerView 布局方式为横向布局
//        mHeaderProductsRv.setLayoutManager(ms); // 给 RecyclerView 添加设置好的布局样式

        mHeaderProductsRv.setLayoutManager(new GridLayoutManager(getActivity(), 3));//这里用线性宫格显示 类似于grid view
        mHeaderProductsRv.setAdapter(mProductsAdapter);

        mProductsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            Loger.debug("mProductsAdapter 新品");
            startActivity(new Intent(getActivity(), BannerActivity.class));
        });

        mHeaderTopicRv.setLayoutManager(new GridLayoutManager(getActivity(), 4));//这里用线性宫格显示 类似于grid view
        mHeaderTopicRv.setAdapter(mTopicAdapter);

        mTopicAdapter.setOnItemClickListener((adapter, view, position) -> {

            int mallType = 0;
            if (position < 7) {
                mallType = position + 1;
            }

            Loger.debug("传递过去的类型是：" + mallType);
            Intent intent = new Intent(getActivity(), MallListActivity.class);
            intent.putExtra(Constant.MALL_TYPE, mallType);
            startActivity(intent);
        });

    }

    @Override
    public void getBookSuccess(Movie movie) {
        if (movie != null && movie.getSubjects().size() > 0) {
            List<Movie.SubjectsBean> list = movie.getSubjects();
            if (thePageIndex == 0) {
                mAdapter.setNewData(list);
                refreshLayout.finishRefresh(200, true);

                // 头部的列表测试数据
                mProductsAdapter.addData(list.subList(0, 3));
            } else {
                mAdapter.addData(list);
                if (mAdapter.getData().size() < movie.getTotal()) {
                    mAdapter.loadMoreComplete();
                } else {
                    mAdapter.loadMoreEnd();
                }
            }
        } else {
            if (thePageIndex == 0) {
                refreshLayout.finishRefresh(200, false);
            } else {
                mAdapter.loadMoreFail();
            }
        }
        Loger.debug("mAdapter data" + mAdapter.getData().size());
        thePageIndex += 1;

//        加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
//                mQuickAdapter.loadMoreComplete();
//        加载失败 mQuickAdapter.loadMoreFail();
//        加载结束 mQuickAdapter.loadMoreEnd();
    }

    @Override
    public void getBookFailed(ParseErrorMsgUtil.ErrorMessage errorMessage) {
        ToastUtils.showShort(errorMessage.toString());
        if (thePageIndex == 0) {
            refreshLayout.finishRefresh(false);
        } else {
            mAdapter.loadMoreFail();
        }
    }

    @Override
    public void showDialog(String title, String content) {

    }

    @Override
    public void hideDialog() {

    }


    // banner 样式------------------------------------------------------------------------------


    // 顶部滑动样式------------------------------------------------------------------------------

    /**
     * 优化滚动时glide
     */
    private void changeTopSearchStyle() {

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (getActivity() != null) {
                    switch (newState) {
                        case 0:
                        case 1:
                            Glide.with(getActivity()).resumeRequests();
                            break;
                        case 2:
                            Glide.with(getActivity()).pauseRequests();
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        Loger.debug("Fragment onVisible");

    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        Loger.debug("Fragment onInvisible");
    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
