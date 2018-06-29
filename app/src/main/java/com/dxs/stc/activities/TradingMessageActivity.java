package com.dxs.stc.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.adpater.stickyheader.GroupInfo;
import com.dxs.stc.adpater.stickyheader.StickySectionDecoration;
import com.dxs.stc.adpater.stickyheader.TestAdapter;
import com.dxs.stc.base.CompatStatusBarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TradingMessageActivity extends CompatStatusBarActivity {

    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;
    @BindView(R.id.stickyheader_recyclerview)
    RecyclerView mRecyclerView;

    List<String> data;
    TestAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trading_message);
        getWindow().setBackgroundDrawableResource(R.color.color_E6);
        ButterKnife.bind(this);
        setStatus(true, true, ContextCompat.getColor(this, R.color.navColor));
    }

    @OnClick({R.id.iv_bar_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_left:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        mTitleCenter.setText(getString(R.string.trading_message));

        initDatas();

        mAdapter = new TestAdapter(R.layout.item_trading_message, data);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutmanager);
        StickySectionDecoration.GroupInfoCallback callback = new StickySectionDecoration.GroupInfoCallback() {
            @Override
            public GroupInfo getGroupInfo(int position) {

                /**
                 * 分组逻辑，这里为了测试每 5 个数据为一组。大家可以在实际开发中
                 * 替换为真正的需求逻辑
                 */
                int groupId = position / 5;
                int index = position % 5;
                GroupInfo groupInfo = new GroupInfo(groupId, "2018-6-28" + groupId);
                groupInfo.setGroupLength(5);
                groupInfo.setPosition(index);
                return groupInfo;
            }
        };
        mRecyclerView.addItemDecoration(new StickySectionDecoration(this, callback));
    }

    /**
     * 初始化测试数据
     */
    private void initDatas() {
        data = new ArrayList<>();
        for (int i = 0; i < 56; i++) {
            data.add(i + " test ");
        }
    }
}
