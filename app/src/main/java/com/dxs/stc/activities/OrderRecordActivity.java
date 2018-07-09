package com.dxs.stc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.adpater.CommodityOrderRecordAdapter;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.base.Constant;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.mvp.presenter.IGetBookPresenter;
import com.dxs.stc.mvp.presenter.impl.GetBookPresenterImpl;
import com.dxs.stc.mvp.view.IBookView;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.utils.decoration.HorizontalDividerItemDecoration;
import com.dxs.stc.utils.http.ParseErrorMsgUtil;
import com.dxs.stc.widget.tablayout.XTabLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderRecordActivity extends CompatStatusBarActivity implements IBookView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.tl_record_type)
    XTabLayout mTabLayout;

    @BindView(R.id.tv_bar_left)
    TextView mBarLeft;
    @BindView(R.id.tv_bar_right)
    TextView mBarRight;

    private CommodityOrderRecordAdapter mAdapter;
    List<Movie.SubjectsBean> mData;

    private IGetBookPresenter iGetBookPresenter;
    private int thePageIndex = 0;
    private LinearLayoutManager linearLayoutManager;

    private boolean isProductOrder = true;
    private String orderType;
    private int orderStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_record);
        getWindow().setBackgroundDrawableResource(R.color.white);
        ButterKnife.bind(this);
        setStatus(true, true, ContextCompat.getColor(this, R.color.navColor));
        getIntentData();
        initView();
    }

    private void getIntentData() {

        Intent intent = getIntent();
        orderType = intent.getStringExtra(Constant.ORDER_TYPE);
        orderStatus = intent.getIntExtra(Constant.ORDER_STATUS, 0);
        Loger.debug("orderType:" + orderType + " == orderStatus:" + orderStatus);
    }

    @OnClick({R.id.iv_bar_left, R.id.tv_bar_left, R.id.tv_bar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_left:
                onBackPressed();
                break;
            case R.id.tv_bar_left:
                isProductOrder = true;
                whichOrderType();
                break;
            case R.id.tv_bar_right:
                isProductOrder = false;
                whichOrderType();
                break;
        }
    }


    private void whichOrderType() {
        mBarLeft.setSelected(isProductOrder);
        mBarRight.setSelected(!isProductOrder);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        mData = new ArrayList<>();

        XTabLayout.Tab tabAll = mTabLayout.newTab().setText(getString(R.string.order_all));
        XTabLayout.Tab tabPendingPayment = mTabLayout.newTab().setText(getString(R.string.pending_payment));
        XTabLayout.Tab tabWaitingShipped = mTabLayout.newTab().setText(getString(R.string.waiting_to_be_shipped));
        XTabLayout.Tab tabWaitingReceipt = mTabLayout.newTab().setText(getString(R.string.waiting_for_receipt));
        XTabLayout.Tab tabBeenDelivered = mTabLayout.newTab().setText(getString(R.string.had_been_delivered));
        mTabLayout.addTab(tabAll);
        mTabLayout.addTab(tabPendingPayment);
        mTabLayout.addTab(tabWaitingShipped);
        mTabLayout.addTab(tabWaitingReceipt);
        mTabLayout.addTab(tabBeenDelivered);

        isProductOrder = true;
        whichOrderType();

//        mTabLayout.removeTabAt(0);
//        mTabLayout.addTab(tabAll);

        iGetBookPresenter = new GetBookPresenterImpl(this);
        mAdapter = new CommodityOrderRecordAdapter(R.layout.item_commodity_order_record, mData);
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
            Loger.debug("点击了订单项");
            startActivity(new Intent(OrderRecordActivity.this, OrderDetailsActivity.class));
        });

        mTabLayout.getTabAt(orderStatus).select();
        mTabLayout.addOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                //tab被选的时候回调
                // 新数据请求
                Loger.debug("tab onTabSelected:" + tab.getPosition());
                orderStatus = tab.getPosition();
                setWhichStatus();
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });
    }

    private void setWhichStatus() {
        Loger.debug("setWhichStatus orderStatus: " + orderStatus);
        switch (orderStatus) {
            case 0:
                // 全部订单
                break;
            case 1:
                // 待付款订单
                break;
            case 2:
                // 待发货订单
                break;
            case 3:
                // 待收货订单
                break;
            case 4:
                // 已完成订单
                break;
        }
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
