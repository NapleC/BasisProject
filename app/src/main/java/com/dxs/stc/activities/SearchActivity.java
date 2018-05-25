package com.dxs.stc.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.adpater.SearchHistoryAdapter;
import com.dxs.stc.adpater.SearchWannaAdapter;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.widget.EditTextWithAnimator;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends CompatStatusBarActivity {

    @BindView(R.id.iv_back)
    ImageView mSearchBackIv;
    @BindView(R.id.etwa_search)
    EditTextWithAnimator mSearchEt;
    @BindView(R.id.tv_search)
    TextView mHistorySearchTv;
    @BindView(R.id.tv_history_del)
    TextView mHistoryDelTv;
    @BindView(R.id.tv_history_wanna)
    TextView mHistoryWannaTv;
    @BindView(R.id.rv_search_history)
    RecyclerView mHistoryListRv;
    @BindView(R.id.rv_search_wanna)
    RecyclerView mWannaListRv;

    private SearchHistoryAdapter mHistoryAdapter;
    private SearchWannaAdapter mWannaAdapter;

    private List<String> mHistoryTags;
    private List<String> mWannaTags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setStatus();

        initView();
    }

    private void setStatus() {
        setStatusBarPlaceVisible(true);
        setViewColorStatusBar(true, getResources().getColor(R.color.navColor));
    }

    private void initView() {

        mHistoryTags = new ArrayList<>();
        mWannaTags = new ArrayList<>();
        mWannaTags.add(" 这个杀手不太冷 ");
        mWannaTags.add(" 阿甘正传 ");
        mWannaTags.add(" 十二怒汉 ");
        mWannaTags.add(" 海上钢琴师 ");
        mWannaTags.add(" 搏击俱乐部 ");
        mWannaTags.add(" 忠犬八公的故事 ");
        mWannaTags.add(" 卡比利亚之夜 ");


        mHistoryAdapter = new SearchHistoryAdapter(R.layout.tag_search_tv, mHistoryTags);
        mWannaAdapter = new SearchWannaAdapter(R.layout.tag_search_tv, mWannaTags);
        FlexboxLayoutManager historyLayoutManager = new FlexboxLayoutManager(this);
        FlexboxLayoutManager wannaLayoutManager = new FlexboxLayoutManager(this);
        historyLayoutManager.setFlexDirection(FlexDirection.ROW);
        wannaLayoutManager.setFlexDirection(FlexDirection.ROW);
        mHistoryListRv.setLayoutManager(historyLayoutManager);
        mWannaListRv.setLayoutManager(wannaLayoutManager);

        mHistoryListRv.setAdapter(mHistoryAdapter);
        mWannaListRv.setAdapter(mWannaAdapter);
    }



    @OnClick({R.id.iv_back, R.id.tv_search,R.id.tv_history_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ToastUtils.showShort("点击返回按钮");
                break;
            case R.id.tv_search:
                if (mSearchEt.getText().toString().isEmpty()) {
                    ToastUtils.showShort("搜索内容不能为空！");
                } else {
                    mHistoryDelTv.setVisibility(View.VISIBLE);
                    mHistoryListRv.setVisibility(View.VISIBLE);
                    mHistoryAdapter.addData(mSearchEt.getText().toString());
                }

                break;
            case R.id.tv_history_del:
                ToastUtils.showShort("删除搜索历史");
                mHistoryTags = mHistoryAdapter.getData();
                Loger.debug("mHistoryTags:"+mHistoryTags.size());
                mHistoryTags.clear();
                mHistoryAdapter.setNewData(mHistoryTags);
                mHistoryDelTv.setVisibility(View.GONE);
                mHistoryListRv.setVisibility(View.GONE);
                break;
        }
    }
}
