package com.dxs.stc.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dxs.stc.R;
import com.dxs.stc.activities.BannerActivity;
import com.dxs.stc.activities.MainActivity;
import com.dxs.stc.activities.SearchActivity;
import com.dxs.stc.adpater.HomeRecyclerViewAdapter;
import com.dxs.stc.base.Constant;
import com.dxs.stc.base.LazyBaseFragment;
import com.dxs.stc.dialog.AuctionResultDialog;
import com.dxs.stc.dialog.NiftyDialog;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.mvp.presenter.IGetBookPresenter;
import com.dxs.stc.mvp.presenter.impl.GetBookPresenterImpl;
import com.dxs.stc.mvp.view.IBookView;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.SPUtil;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.utils.http.ParseErrorMsgUtil;
import com.dxs.stc.widget.CustomTextOnPic;
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
 * FragmentHome
 */

public class FragmentHome extends LazyBaseFragment implements IBookView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_bar_text)
    TextView mBarText;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private HomeRecyclerViewAdapter mAdapter;
    List<Movie.SubjectsBean> mData;

    private IGetBookPresenter iGetBookPresenter;

    private Unbinder unbinder;

    private View mHeaderView;
    private RelativeLayout mTopNoticeArea;
    private CustomTextOnPic mCustomAuction1;
    private CustomTextOnPic mCustomAuction2;
    private CustomTextOnPic mCustomAuction3;
    private TextView mJewelryText;
    private TextView mOrnamentsText;

    String[] images = new String[]{
            "https://image2.wbiao.co/upload/default/201702/07/1486396886665233850.jpg",
            "https://image2.wbiao.co/upload/default/201702/07/1486396886895810368.jpg",
            "https://image2.wbiao.co/upload/article/201702/17/1487322373441497976.jpg",
            "https://image2.wbiao.co/upload/default/201702/15/1487138933042431801.jpg"};

    private int thePageIndex = 0;

    public static FragmentHome newInstance() {
        return new FragmentHome();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        initViews();
    }

    private void initViews() {
        mBarText.setText(getString(R.string.title_home));
        mData = new ArrayList<>();
        mAdapter = new HomeRecyclerViewAdapter(R.layout.item_home_book, mData);

        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header_home, null);
        mAdapter.setHeaderView(mHeaderView);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        SpacesItemDecoration decoration = new SpacesItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.dp_14),
                getResources().getDimensionPixelSize(R.dimen.dp_10), 1);
        mRecyclerView.addItemDecoration(decoration);

        mRecyclerView.setAdapter(mAdapter);

        iGetBookPresenter = new GetBookPresenterImpl(this);
        refreshLayout.autoRefresh();

        changeTopSearchStyle();
        initHeaderView();

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
                MainActivity parentActivity = (MainActivity) getActivity();
                assert parentActivity != null;
                parentActivity.toNewsTab(3);
                break;
            case R.id.iv_bar_left:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }

    private void initHeaderView() {

        Banner mTopBanner = mHeaderView.findViewById(R.id.home_banner);
        mTopNoticeArea = mHeaderView.findViewById(R.id.ll_notice_area);
        mCustomAuction1 = mHeaderView.findViewById(R.id.ctp_in_auction1);
        mCustomAuction2 = mHeaderView.findViewById(R.id.ctp_in_auction2);
        mCustomAuction3 = mHeaderView.findViewById(R.id.ctp_in_auction3);
        mJewelryText = mHeaderView.findViewById(R.id.tv_topic3_jewelry);
        mOrnamentsText = mHeaderView.findViewById(R.id.tv_topic3_ornaments);

        mTopNoticeArea.setOnClickListener(v -> {
//            ToastUtils.showShort("进入预告");
//            mCustomAuction1.setRoundImageUrl(getActivity(), "https://image2.wbiao.co/upload/article/201702/17/1487323723401153512.jpg");
//            mCustomAuction1.setTipText("拍卖已结束");
            new AuctionResultDialog(getActivity(), R.style.dialog, new AuctionResultDialog.OnDialogListener() {
                @Override
                public void onClick(Dialog dialog, boolean isComplete) {

                }
            }).show();
        });
        mCustomAuction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BannerActivity.class));
            }
        });
        mCustomAuction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BannerActivity.class));
            }
        });
        mCustomAuction3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BannerActivity.class));
            }
        });

        mJewelryText.setOnClickListener(v -> ToastUtils.showShort("进入珠宝页面"));
        mOrnamentsText.setOnClickListener(v -> ToastUtils.showShort("进入摆件页面"));

        mTopBanner.setImages(Arrays.asList(images)).setImageLoader(new GlideImageLoad()).start();

    }

    @Override
    public void getBookSuccess(Movie movie) {
        if (movie != null && movie.getSubjects().size() > 0) {
            List<Movie.SubjectsBean> list = movie.getSubjects();
            if (thePageIndex == 0) {
                mAdapter.setNewData(list);
                refreshLayout.finishRefresh(200, true);
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


    // banner 样式-----------------------------------------------------------------------------


    // 顶部滑动样式-----------------------------------------------------------------------------

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

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
