package com.dxs.stc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import com.dxs.stc.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MallListActivity extends CompatStatusBarActivity implements IBookView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private MallListAdapter mListAdapter;
    private MallGridAdapter mGridAdapter;
    List<Movie.SubjectsBean> mData;

    private IGetBookPresenter iGetBookPresenter;
    private int goodsType = 0;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    SpacesItemDecoration decoration;
    private int needScrollPosition = 0;

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


    @OnClick({R.id.iv_back, R.id.tv_switch})
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
        }
    }

    private void setListAdapter(boolean needScroll) {
        needScrollPosition = gridLayoutManager.findFirstVisibleItemPosition();
        Loger.debug("setListAdapter needScrollPosition:"+needScrollPosition);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);
        if (needScroll){
            moveToPosition(linearLayoutManager, mRecyclerView, needScrollPosition);
        }
        goodsType = 1;

    }

    private void setGridAdapter() {
        needScrollPosition = linearLayoutManager.findFirstVisibleItemPosition();
        Loger.debug("setGridAdapter needScrollPosition:"+needScrollPosition);
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
