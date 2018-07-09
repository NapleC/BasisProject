package com.dxs.stc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.adpater.AddressManagerAdapter;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.bean.AddressBean;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.decoration.HorizontalDividerItemDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressManagerActivity extends CompatStatusBarActivity {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;

    private AddressManagerAdapter mAdapter;
    private List<AddressBean> mData;
    private LinearLayoutManager linearLayoutManager;
    private int isCheckPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager);
        getWindow().setBackgroundDrawableResource(R.color.color_E6);
        ButterKnife.bind(this);
        setStatus(true, true, ContextCompat.getColor(this, R.color.navColor));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTitleCenter.setText(getString(R.string.address_manager));

        initAdapter();
    }

    private void initAdapter() {

        mData = new ArrayList<>();
        mData.clear();
        initData();
        mAdapter = new AddressManagerAdapter(R.layout.item_address, mData);
        mRecyclerView.setAdapter(mAdapter);

        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(ContextCompat.getColor(this, R.color.transparent))
                        .sizeResId(R.dimen.dp_10)
                        .build());

//        refreshLayout.autoRefresh();
//
//        refreshLayout.finishRefresh(200, true);

        refreshLayout.setOnRefreshListener(refreshlayout -> {
            // 需要根据不同的 mallType做请求。
            Loger.debug("onRefresh the start:");
        });

        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setEnableLoadMore(false);

        for (int i = 0, lenD = mData.size(); i < lenD; i++) {
            if (mData.get(i).isDefault()) {
                isCheckPosition = i;
                break;
            }
        }
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Loger.debug("setOnItemClickListener");
            startActivity(new Intent(AddressManagerActivity.this, EditAddressActivity.class));
        });
        mAdapter.setCheckItemListener(position -> {
            mData.get(isCheckPosition).setDefault(false);
            mData.get(position).setDefault(true);
            mAdapter.setData(isCheckPosition, mData.get(isCheckPosition));
            mAdapter.setData(position, mData.get(position));
            isCheckPosition = position;
        });
    }

    private void initData() {
        AddressBean ab1 = new AddressBean(10010, "张三", "13066812127",
                "深圳市福田区", "奥士达大厦", true);
        mData.add(ab1);
        AddressBean ab2 = new AddressBean(10010, "李四", "13066812127",
                "深圳市南山区", "华侨村", false);
        mData.add(ab2);
        AddressBean ab3 = new AddressBean(10010, "王五", "13066812127",
                "深圳市前海湾", "蛇口", false);
        mData.add(ab3);
    }

    @OnClick({R.id.iv_bar_left, R.id.btn_add_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_left:
                onBackPressed();
                break;
            case R.id.btn_add_address:
                Loger.debug("is check position :" + isCheckPosition);
                Loger.debug("数据：" + mData.toString());
                startActivity(new Intent(AddressManagerActivity.this, EditAddressActivity.class));
                break;
        }
    }
}
