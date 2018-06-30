package com.dxs.stc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.adpater.NoticeAdapter;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.mvp.presenter.IGetBookPresenter;
import com.dxs.stc.mvp.presenter.impl.GetBookPresenterImpl;
import com.dxs.stc.mvp.view.IBookView;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.utils.decoration.HorizontalDividerItemDecoration;
import com.dxs.stc.utils.http.ParseErrorMsgUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticeActivity extends CompatStatusBarActivity implements IBookView {

    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.tv_notice_today)
    TextView mTodayNoticeTv;
    @BindView(R.id.tv_notice_last)
    TextView mLastNoticeTv;

    private NoticeAdapter mAdapter;
    List<Movie.SubjectsBean> mData;

    private IGetBookPresenter iGetBookPresenter;
    private int thePageIndex = 0;
    private LinearLayoutManager linearLayoutManager;

    private boolean isTodayNotice = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        getWindow().setBackgroundDrawableResource(R.color.color_E6);
        ButterKnife.bind(this);
        setStatus(true, true, ContextCompat.getColor(this, R.color.navColor));
    }

    @OnClick({R.id.iv_bar_left, R.id.tv_notice_today, R.id.tv_notice_last})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_left:
                onBackPressed();
                break;
            case R.id.tv_notice_today:
                isTodayNotice = true;
                todayNotice();
                break;
            case R.id.tv_notice_last:
                isTodayNotice = false;
                todayNotice();
                break;
        }
    }

    private void todayNotice(){
        mTodayNoticeTv.setSelected(isTodayNotice);
        mLastNoticeTv.setSelected(!isTodayNotice);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        mTitleCenter.setText(getString(R.string.title_notice));
        mData = new ArrayList<>();

        isTodayNotice = true;
        todayNotice();

        iGetBookPresenter = new GetBookPresenterImpl(this);
        mAdapter = new NoticeAdapter(R.layout.item_notice, mData);
        mRecyclerView.setAdapter(mAdapter);

        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(ContextCompat.getColor(this, R.color.color_E6))
                        .sizeResId(R.dimen.dp_10)
                        .build());

        refreshLayout.autoRefresh();
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            thePageIndex = 0;
            Loger.debug("onRefresh the start:" + thePageIndex);
            iGetBookPresenter.getBook(10 * thePageIndex, 10);
        });

        // 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法
        mAdapter.setPreLoadNumber(3);
        mAdapter.setOnLoadMoreListener(() -> {
            Loger.debug("onLoadMore the start:" + thePageIndex);
            iGetBookPresenter.getBook(10 * thePageIndex, 10);
        }, mRecyclerView);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Loger.debug("预告列表");
            startActivity(new Intent(NoticeActivity.this, NoticeDetailsActivity.class));
        });
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
}
