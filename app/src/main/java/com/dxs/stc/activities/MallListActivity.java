package com.dxs.stc.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<Map<String,String>> menuData1;

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

//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Loger.debug("adapter onItemClick");
//                ToastUtils.showShortSafe("点击的是第：" + position);
//            }
//        });
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


        initMenuData();
        initPopMenu();
        setNavStyle(0);
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
                setNavStyle(0);
                break;
            case R.id.sort_volume:
                setNavStyle(1);
                break;
            case R.id.sort_price:
                setNavStyle(2);
                break;
        }
    }


    //-------------------------------------筛选样式 begin----------------------------------------

    private void initMenuData() {
        menuData1 = new ArrayList<Map<String, String>>();
        String[] menuStr1 = new String[] { "全部", "粮油", "衣服", "图书", "电子产品",
                "酒水饮料", "水果" };
        Map<String, String> map1;
        for (int i = 0, len = menuStr1.length; i < len; ++i) {
            map1 = new HashMap<String, String>();
            map1.put("name", menuStr1[i]);
            menuData1.add(map1);
        }
    }
    private void initPopMenu(){

        String[] list1 = new String[]{"1","2","3"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, list1);
    }

    private void setNavStyle(int clickIndex) {

        if (selTopIndex == clickIndex) {
            // 再次选中当前选项
            if (topIsRise) {
                Loger.debug(clickIndex + " 选择改为从低到高,(关闭类别选择)");
            } else {
                Loger.debug(clickIndex + "选择改为从高到低,(点开类别选择)");
            }
        } else {
            // 从其他选项切换至当前选项
            topIsRise = true;
            if (clickIndex == 0) {
                Loger.debug(clickIndex + "点开类别选择,但不展开");
            } else {
                Loger.debug(clickIndex + "选择 为从低到高");
            }

        }

        switch (clickIndex) {
            case 0:
                mSortType.setSelected(true);
                mSortVolume.setSelected(false);
                mSortPrice.setSelected(false);
                mSortType.setSortIsRise(topIsRise);
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

    //----------------------------------旋转动画------------------------------------
    //当前旋转的度数
    private int rotate = 0;
    //每次旋转的度数
    private int rotation = 360;
    //判断顺时针转还是逆时针转
    private boolean rotateDirection = true;

    /**
     * 悬浮菜单动画效果
     * @param v
     */
    private void mRotate(View v) {

        ObjectAnimator animator;

        //判断是顺时针旋转还是逆时针旋转
        if(rotateDirection){
            animator = ObjectAnimator.ofFloat(v, "rotation", rotate,rotate-rotation);
            rotate = rotate+rotation;

        }else{
            animator = ObjectAnimator.ofFloat(v, "rotation", rotate,rotate+rotation);
            rotate = rotate-rotation;
        }

        //持续时间
        animator.setDuration(350);
        animator.start();
        rotateDirection = !rotateDirection;
    }
}
