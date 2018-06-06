package com.dxs.stc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dxs.stc.R;
import com.dxs.stc.adpater.MallGridAdapter;
import com.dxs.stc.adpater.MallListAdapter;
import com.dxs.stc.base.CompatStatusBarActivity;
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

import java.util.ArrayList;
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

    private MallListAdapter mListAdapter;
    private MallGridAdapter mGridAdapter;
    List<Movie.SubjectsBean> mData;

    private IGetBookPresenter iGetBookPresenter;
    private int goodsType = 0;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    SpacesItemDecoration decoration;
    private int needScrollPosition = 0;

    private int selTopIndex = -1;
    private boolean topIsRise = false;

    List<String> typeItems;
    private TopMiddlePopup middlePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_list);
        ButterKnife.bind(this);
        setStatus();
        initView();
    }

    private void setStatus() {
        setStatusBarPlaceVisible(true);
        setViewColorStatusBar(true, getResources().getColor(R.color.navColor));
    }

    private void initView() {

        mData = new ArrayList<>();
        mListAdapter = new MallListAdapter(R.layout.item_mall_list, mData);
        mGridAdapter = new MallGridAdapter(R.layout.item_mall_grid, mData);

        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        decoration = new SpacesItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.dp_14),
                getResources().getDimensionPixelSize(R.dimen.dp_10), 0);

        setListAdapter(false);
        iGetBookPresenter = new GetBookPresenterImpl(this);
        iGetBookPresenter.getBook(10, 10);

        mListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Loger.debug("列表模式点击的是第：" + position);
                ToastUtils.showShortSafe("列表模式点击的是第：" + position);
            }
        });

        mGridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Loger.debug("宫格模式点击的是第：" + position);
                ToastUtils.showShortSafe("宫格模式点击的是第：" + position);
            }
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
    }


    @Override
    public void getBookSuccess(Movie movie) {
        List<Movie.SubjectsBean> list = movie.getSubjects();
        mGridAdapter.addData(list);
        mListAdapter.addData(list);
    }

    @Override
    public void getBookFailed(ParseErrorMsgUtil.ErrorMessage errorMessage) {
        ToastUtils.showShort(errorMessage.toString());
    }

    @Override
    public void showDialog(String title, String content) {

    }

    @Override
    public void hideDialog() {

    }

    @OnClick({R.id.iv_back, R.id.tv_switch, R.id.sort_type, R.id.sort_volume, R.id.sort_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
//                onBackPressed();
                startActivity(new Intent(MallListActivity.this, LoginActivity.class));
                break;
            case R.id.tv_switch:
                // 切换开关
                if (goodsType == 0) {
                    setListAdapter(true);
                } else {
                    setGridAdapter();
                }
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


    //-------------------------------------筛选样式 begin----------------------------------------

    /**
     * 设置弹窗内容
     *
     * @return
     */
    private void getItemsName() {

        typeItems = new ArrayList<String>();
        String[] topicTitle = getResources().getStringArray(R.array.mall_header_topic_title);
        for (int k = 0, lenK = topicTitle.length; k < lenK; k++) {

            typeItems.add(topicTitle[k]);
        }
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
            }

            @Override
            public void onDismiss() {
                Loger.debug("middlePopup is close topIsRise:" + topIsRise);
                setNavStyle(selTopIndex);
            }
        });
    }

    private void setNavChange(int clickIndex) {

        Loger.debug("setNavChange" + clickIndex);
        if (selTopIndex == clickIndex) {
            switch (clickIndex) {
                case 0:
                    if (!middlePopup.isShowing()) {
                        middlePopup.showPop(mSortType);
//                        showListPopupWindow(mSortType);
                        Loger.debug(clickIndex + "点开类别选择");
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
        Loger.debug("topIsRise:" + topIsRise);
        topIsRise = !topIsRise;
        selTopIndex = clickIndex;
    }

    //-------------------------------------筛选样式 end----------------------------------------

    private void setListAdapter(boolean needScroll) {
        needScrollPosition = gridLayoutManager.findFirstVisibleItemPosition();
        Loger.debug("setListAdapter needScrollPosition:" + needScrollPosition);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);
        if (needScroll) {
            moveToPosition(linearLayoutManager, mRecyclerView, needScrollPosition);
        }
        goodsType = 1;

    }

    private void setGridAdapter() {
        needScrollPosition = linearLayoutManager.findFirstVisibleItemPosition();
        Loger.debug("setGridAdapter needScrollPosition:" + needScrollPosition);
        mRecyclerView.setLayoutManager(gridLayoutManager);//这里用线性宫格显示 类似于grid view
        mRecyclerView.setAdapter(mGridAdapter);
        moveToPosition(gridLayoutManager, mRecyclerView, needScrollPosition);
        goodsType = 0;
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
