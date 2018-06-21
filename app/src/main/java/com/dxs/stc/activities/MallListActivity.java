package com.dxs.stc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.dxs.stc.R;
import com.dxs.stc.adpater.MallMoreAdapter;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.base.Constant;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.mvp.presenter.IGetBookPresenter;
import com.dxs.stc.mvp.presenter.impl.GetBookPresenterImpl;
import com.dxs.stc.mvp.view.IBookView;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.utils.http.ParseErrorMsgUtil;
import com.dxs.stc.widget.CustomSortArrow;
import com.dxs.stc.widget.SpacesItemDecoration;
import com.dxs.stc.widget.TopMiddlePopup;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MallListActivity extends CompatStatusBarActivity implements IBookView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.sort_type)
    CustomSortArrow mSortType;
    @BindView(R.id.sort_volume)
    CustomSortArrow mSortVolume;
    @BindView(R.id.sort_price)
    CustomSortArrow mSortPrice;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private MallMoreAdapter mAdapter;
    List<Movie.SubjectsBean> mData;

    private IGetBookPresenter iGetBookPresenter;
    private boolean typeIsGrid = false;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    SpacesItemDecoration decoration;

    private int selTopIndex = -1;
    private boolean topIsRise = false;

    List<String> typeItems;
    private TopMiddlePopup middlePopup;
    private int thePageIndex = 0;
    private int mallType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_list);
        ButterKnife.bind(this);
        setStatus();
        getIntentData();
    }

    private void setStatus() {
        setStatusBarPlaceVisible(true);
        setViewColorStatusBar(true, getResources().getColor(R.color.navColor));
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mallType = intent.getIntExtra(Constant.MALL_TYPE, 0);
        Loger.debug("传递得到的mallType： " + mallType);
    }

    @Override
    protected void onStart() {
        super.onStart();
        selTopIndex = -1;
        initView();
    }

    private void initView() {

        mData = new ArrayList<>();
        mData.clear();
        mAdapter = new MallMoreAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);

        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        decoration = new SpacesItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.dp_14),
                getResources().getDimensionPixelSize(R.dimen.dp_10), 0);

        setRecyclerViewLayoutManager();
        iGetBookPresenter = new GetBookPresenterImpl(this);
        mAdapter.setOnItemClickListener(position -> {
            Loger.debug("点击的是第：" + position);
            ToastUtils.showShortSafe("点击的是第：" + position);

        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {

                    case 0:
                    case 1:
                        Glide.with(MallListActivity.this).resumeRequests();
                        break;
                    case 2:
                        Glide.with(MallListActivity.this).pauseRequests();
                        break;
                }

            }
        });

        initPopMenu();
        setNavChange(0);

        refreshLayout.setOnRefreshListener(refreshlayout -> {
            thePageIndex = 0;
            // 需要根据不同的 mallType做请求。
            Loger.debug("onRefresh the start:" + thePageIndex);
            iGetBookPresenter.getBook(10 * thePageIndex, 10);
        });

        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            Loger.debug("onLoadMore the start:" + thePageIndex);
            iGetBookPresenter.getBook(10 * thePageIndex, 10);
        });
        getNewTypeData();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.setOnItemClickListener(null);
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
                refreshLayout.finishLoadMore(200, true, false);
            }
        } else {
            if (thePageIndex == 0) {
                refreshLayout.finishRefresh(200, false);
            } else {
                refreshLayout.finishLoadMore(200, false, false);
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
            refreshLayout.finishLoadMore(false);
        }
    }

    @Override
    public void showDialog(String title, String content) {
        if (thePageIndex == 0) {
            showLoading();
        }
    }

    @Override
    public void hideDialog() {
        if (thePageIndex == 0) {
            hideLoading();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_switch, R.id.sort_type, R.id.sort_volume, R.id.sort_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
//                startActivity(new Intent(MallListActivity.this, LoginActivity.class));
                break;
            case R.id.tv_switch:
                setRecyclerViewLayoutManager();
                break;
            case R.id.sort_type:
                setNavChange(0);
                break;
            case R.id.sort_volume:
                setNavChange(1);
                break;
            case R.id.sort_price:
                setNavChange(2);
                break;
        }
    }


    private void getNewTypeData(){

//        iGetBookPresenter.getBook(10 * thePageIndex, 10);
        mSortType.setTitleText(typeItems.get(mallType));
        refreshLayout.autoRefresh();
    }
    //-------------------------------------筛选样式 begin----------------------------------------

    /**
     * 设置弹窗内容
     */
    private void getItemsName() {

        if (typeItems == null) {
            typeItems = new ArrayList<>();
        }
        typeItems.clear();
        typeItems.add("全部");
        String[] topicTitle = getResources().getStringArray(R.array.mall_header_topic_title);
        typeItems.addAll(Arrays.asList(topicTitle));
    }

    private void initPopMenu() {
        getItemsName();
        if (middlePopup == null) {
            middlePopup = new TopMiddlePopup(MallListActivity.this, typeItems);
        }
        middlePopup.setOnCustomDismissListener(new TopMiddlePopup.OnCustomDismissListener() {
            @Override
            public void clickPosition(int position) {
                Loger.debug("选中的是第" + position + "个");
                ToastUtils.showShort("选择了" + typeItems.get(position));
                middlePopup.dismiss();
                setNavStyle(selTopIndex);
                mallType = position;
                getNewTypeData();
            }

            @Override
            public void onDismiss() {
                Loger.debug("middlePopup is close topIsRise:" + topIsRise);
                setNavStyle(selTopIndex);
            }
        });
    }

    private void setNavChange(int clickIndex) {

        Loger.debug("topIsRise:" + topIsRise);
        Loger.debug("setNavChange" + clickIndex);
        if (selTopIndex == clickIndex) {
            switch (clickIndex) {
                case 0:
                    if (!middlePopup.isShowing()) {
                        middlePopup.showPop(mSortType);
                        Loger.debug(clickIndex + "点开类别选择");
                    } else {
                        Loger.debug(clickIndex + "关闭类别选择");
                        middlePopup.dismiss();
                        setNavStyle(selTopIndex);
                    }
                    break;
                case 1:
                    if (topIsRise) {
                        Loger.debug(clickIndex + "销量选择 改为从低到高");
                    } else {
                        Loger.debug(clickIndex + "销量选择改为从高到低");
                    }
                    break;
                case 2:
                    if (topIsRise) {
                        Loger.debug(clickIndex + "价格选择 改为从低到高");
                    } else {
                        Loger.debug(clickIndex + "价格选择改为从高到低");
                    }
                    break;
            }
        } else {
            // 从其他选项切换至当前选项
            topIsRise = true;
            switch (clickIndex) {
                case 0:
                    Loger.debug(clickIndex + "点开类别选择,但不展开");
                    break;
                case 1:
                    Loger.debug(clickIndex + "销量选择 从低到高");
                    break;
                case 2:
                    Loger.debug(clickIndex + "价格选择 从低到高");
                    break;
            }

        }
        setNavStyle(clickIndex);
    }

    private void setNavStyle(int clickIndex) {

        Loger.debug("topIsRise:" + topIsRise);
        Loger.debug("setNavStyle" + clickIndex);
        switch (clickIndex) {
            case 0:
                mSortType.setSelected(true);
                mSortVolume.setSelected(false);
                mSortPrice.setSelected(false);
                mSortType.setRiseVisible(topIsRise);
                mSortType.setDropVisible(!topIsRise);
                break;
            case 1:
                mSortType.setSelected(false);
                mSortVolume.setSelected(true);
                mSortPrice.setSelected(false);
                mSortVolume.setSortIsRise(topIsRise);
                break;
            case 2:
                mSortType.setSelected(false);
                mSortVolume.setSelected(false);
                mSortPrice.setSelected(true);
                mSortPrice.setSortIsRise(topIsRise);
                break;
        }
        topIsRise = !topIsRise;
        selTopIndex = clickIndex;
    }

    //-------------------------------------筛选样式 end----------------------------------------


    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstVisibleItemPosition();
        }

        mAdapter.setType(typeIsGrid ? 0 : 1);
        if (typeIsGrid) {
            mRecyclerView.setLayoutManager(gridLayoutManager);
            moveToPosition(gridLayoutManager, mRecyclerView, scrollPosition);
        } else {
            mRecyclerView.setLayoutManager(linearLayoutManager);
            moveToPosition(linearLayoutManager, mRecyclerView, scrollPosition);
        }
        typeIsGrid = !typeIsGrid;
    }


    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager       设置RecyclerView对应的manager
     * @param mRecyclerView 当前的RecyclerView
     * @param n             要跳转的位置
     */
    public static void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {

        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }

    }

}
