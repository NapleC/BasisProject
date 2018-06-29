package com.dxs.stc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dxs.stc.R;
import com.dxs.stc.activities.NoticeDetailsActivity;
import com.dxs.stc.adpater.AuctionHeaderSiteAdapter;
import com.dxs.stc.adpater.AuctionRecyclerViewAdapter;
import com.dxs.stc.base.LazyBaseFragment;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.mvp.presenter.IGetBookPresenter;
import com.dxs.stc.mvp.presenter.impl.GetBookPresenterImpl;
import com.dxs.stc.mvp.view.IBookView;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.utils.decoration.SpacesItemDecoration;
import com.dxs.stc.utils.http.ParseErrorMsgUtil;
import com.dxs.stc.widget.GlideImageLoad;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * created by hl at 2018/5/15
 * FragmentAuction
 */
public class FragmentAuction extends LazyBaseFragment implements IBookView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private AuctionRecyclerViewAdapter mAdapter;
    private AuctionHeaderSiteAdapter mSiteAdapter;
    List<Movie.SubjectsBean> mData;

    private IGetBookPresenter iGetBookPresenter;

    private View view;
    private Unbinder unbinder;

    private View mHeaderView;
    private Banner mTopBanner;
    private RecyclerView mHeaderSiteRv;
    List<Movie.SubjectsBean> mSiteData;
    private int thePageIndex = 0;

    String[] images = new String[]{
            "https://image2.wbiao.co/upload/default/201702/07/1486396886665233850.jpg",
            "https://image2.wbiao.co/upload/default/201702/07/1486396886895810368.jpg",
            "https://image2.wbiao.co/upload/article/201702/17/1487322373441497976.jpg",
            "https://image2.wbiao.co/upload/default/201702/15/1487138933042431801.jpg"};


    public static FragmentAuction newInstance() {
        return new FragmentAuction();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_auction, null);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        initViews();
        initHeaderView();
        Loger.debug("mData" + mData.size());
        Loger.debug("mSiteData" + mSiteData.size());
        Loger.debug("mSiteAdapter.getData()" + mSiteAdapter.getData().size());
        getServerData();
    }


    private void getServerData() {

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

//        // 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法
//        mAdapter.setPreLoadNumber(3);
//        mAdapter.setOnLoadMoreListener(() -> {
//            Loger.debug("onLoadMore the start:" + thePageIndex);
//            iGetBookPresenter.getBook(10 * thePageIndex, 10);
//        }, mRecyclerView);


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), NoticeDetailsActivity.class));
            }
        });
    }

    private void initViews() {

        mData = new ArrayList<>();
        mAdapter = new AuctionRecyclerViewAdapter(R.layout.item_auction_book, mData);

        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header_auction, null);
        mAdapter.setHeaderView(mHeaderView);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

        SpacesItemDecoration decoration = new SpacesItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.dp_14),
                getResources().getDimensionPixelSize(R.dimen.dp_10), 1);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void initHeaderView() {

        mSiteData = new ArrayList<>();
        mTopBanner = mHeaderView.findViewById(R.id.auction_banner);
        mHeaderSiteRv = mHeaderView.findViewById(R.id.rv_header_auction_site);
        mTopBanner.setImages(Arrays.asList(images)).setImageLoader(new GlideImageLoad()).start();
        mSiteAdapter = new AuctionHeaderSiteAdapter(R.layout.item_auction_site, mSiteData);

        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 RecyclerView 布局方式为横向布局
        mHeaderSiteRv.setLayoutManager(ms); // 给 RecyclerView 添加设置好的布局样式
        mHeaderSiteRv.setAdapter(mSiteAdapter);

        mSiteAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), NoticeDetailsActivity.class));
            }
        });
    }

    @Override
    public void getBookSuccess(Movie movie) {
        if (movie != null && movie.getSubjects().size() > 0) {
            List<Movie.SubjectsBean> list = movie.getSubjects();
            if (thePageIndex == 0) {
                mAdapter.setNewData(list);
                refreshLayout.finishRefresh(200, true);

                // 头部列表测试数据
                mSiteAdapter.addData(list);
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

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
